package com.danacup.service;

import com.danacup.dto.product.AddProductRequestDto;
import com.danacup.dto.product.FilterProductRequestDto;
import com.danacup.dto.product.ProductDto;
import com.danacup.dto.product.UpdateProductRequestDto;
import com.danacup.entity.*;
import com.danacup.exception.ApiError;
import com.danacup.mapper.ProductMapper;
import com.danacup.repository.ProductEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductEntityRepository productEntityRepository;
    private final ProductMapper productMapper;
    private final UserService userService;
    private final CategoryService categoryService;

    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        List<ProductEntity> productEntities = productEntityRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (ProductEntity productEntity : productEntities) {
            ProductDto productDto = productMapper.toDto(productEntity);
            productDtos.add(productDto);
        }

        return productDtos;
    }

    @Transactional(readOnly = true)
    public ProductEntity findById(Long id) {
        return productEntityRepository.findById(id).orElseThrow(() -> new ApiError.NotFound("product-not-found"));
    }

    @Transactional(readOnly = true)
    public List<ProductEntity> findByIds(List<Long> ids) {
        return productEntityRepository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id) throws ResponseStatusException {
        var product = this.findById(id);
        return productMapper.toDto(product);
    }

    /**
     * Make new product by the admin
     *
     * @param requestDto
     * @return ProductDto details
     */
    @Transactional
    public ProductDto createProduct(AddProductRequestDto requestDto, UserEntity requestedUser) {
        userService.assertUserIsAdmin(requestedUser);
        var category = categoryService.findById(requestDto.categoryId);
        var product = this.saveProduct(
                ProductEntity.builder()
                        .title(requestDto.title)
                        .description(requestDto.description)
                        .price(requestDto.price)
                        .usableBalance(requestDto.usableBalance)
                        .lockedBalance(requestDto.lockedBalance)
                        .category(category)
                        .build()
        );
        logger.info("New product created with id: {}", product.getId());
        return productMapper.toDto(product);
    }

    /**
     * Update the product details by the admin
     *
     * @param id
     * @param requestDto
     * @return ProductDto wtih the new details
     * @throws ResponseStatusException
     */
    @Transactional
    public ProductDto updateProduct(Long id, UpdateProductRequestDto requestDto, UserEntity requestedUser) throws ResponseStatusException {
        userService.assertUserIsAdmin(requestedUser);
        var category = categoryService.findById(requestDto.categoryId);
        var product = this.findById(id);
        product.setTitle(requestDto.title);
        product.setDescription(requestDto.description);
        product.setPrice(requestDto.price);
        product.setUsableBalance(requestDto.usableBalance);
        product.setLockedBalance(requestDto.lockedBalance);
        product.setCategory(category);
        this.saveProduct(product);
        logger.info("product updated with id: {}", product.getId());
        return productMapper.toDto(product);
    }

    /**
     * Delete the product by the admin with id
     *
     * @param id
     * @throws ResponseStatusException
     */
    @Transactional
    public void deleteProduct(Long id, UserEntity requestedUser) throws ResponseStatusException {
        userService.assertUserIsAdmin(requestedUser);
        var product = this.findById(id);
        logger.info("product deleted with id: {}", product.getId());
        productEntityRepository.delete(product);
    }

    /**
     * Increase the product usable balance
     *
     * @param id
     * @param count
     * @return
     * @throws ResponseStatusException
     */
    @Transactional
    public ProductEntity increaseProductUsableBalance(Long id, Integer count) throws ResponseStatusException {
        var product = this.findById(id);
        product.setUsableBalance(product.getUsableBalance() + count);
        this.saveProduct(product);
        logger.info("product usable balance updated with id: {}", product.getId());
        return product;
    }

    /**
     * Decrease product usable balance
     *
     * @param id
     * @param count
     * @return
     * @throws ResponseStatusException
     */
    @Transactional
    public ProductEntity decreaseProductUsableBalance(Long id, Integer count) throws ResponseStatusException {
        var product = this.findById(id);
        product.setUsableBalance(product.getUsableBalance() - count);
        this.saveProduct(product);
        logger.info("product usable balance updated with id: {}", product.getId());
        return product;
    }

    /**
     * Increase product locked balance
     *
     * @param id
     * @param count
     * @return
     * @throws ResponseStatusException
     */
    @Transactional
    public ProductEntity increaseProductLockedBalance(Long id, Integer count) throws ResponseStatusException {
        var product = this.findById(id);
        product.setLockedBalance(product.getLockedBalance() + count);
        this.saveProduct(product);
        logger.info("product usable balance updated with id: {}", product.getId());
        return product;
    }

    /**
     * Decrease product locked balance
     *
     * @param id
     * @param count
     * @return
     * @throws ResponseStatusException
     */
    @Transactional
    public ProductEntity decreaseProductLockedBalance(Long id, Integer count) throws ResponseStatusException {
        var product = this.findById(id);
        product.setLockedBalance(product.getLockedBalance() - count);
        this.saveProduct(product);
        logger.info("product usable balance updated with id: {}", product.getId());
        return product;
    }


    @Transactional(readOnly = true)
    public Page<ProductDto> filterProducts(FilterProductRequestDto filterDto, Pageable pageable) {
        var products = productEntityRepository.findAll((r, q, b) -> {
            var predicates = new ArrayList<Predicate>();
            if (filterDto.title != null) {
                predicates.add(b.like(b.lower(r.get(ProductEntity_.TITLE)), "%" + (filterDto.title.toLowerCase()) + "%"));
            }
            if (filterDto.minPrice != null) {
                predicates.add(b.greaterThanOrEqualTo(r.get(ProductEntity_.price), filterDto.minPrice));
            }
            if (filterDto.maxPrice != null) {
                predicates.add(b.lessThanOrEqualTo(r.get(ProductEntity_.price), filterDto.maxPrice));
            }
            if (filterDto.categoryId != null) {
                predicates.add(b.equal(r.join(ProductEntity_.CATEGORY).get(CategoryEntity_.ID), filterDto.categoryId));
            }

            return b.and(predicates.toArray(new Predicate[0]));
        }, pageable);
        return products.map(productMapper::toDto);
    }
    @Transactional
    public ProductEntity saveProduct(ProductEntity product) {
        return productEntityRepository.save(product);
    }

    /**
     * Assert product version to prevent concurrency
     *
     * @param product
     * @param version
     * @throws ResponseStatusException
     */
    public void assertProductVersion(ProductEntity product, Integer version) throws ResponseStatusException {
        if (!Objects.equals(product.getVersion(), version)) {
            throw new ApiError.Conflict("invalid-product-version");
        }
    }

}
