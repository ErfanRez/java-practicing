package com.danacup.service;

import com.danacup.dto.category.AddCategoryRequestDto;
import com.danacup.dto.category.CategoryDto;
import com.danacup.dto.category.UpdateCategoryRequestDto;
import com.danacup.entity.CategoryEntity;
import com.danacup.entity.UserEntity;
import com.danacup.exception.ApiError;
import com.danacup.mapper.CategoryMapper;
import com.danacup.repository.CategoryEntityRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryEntityRepository categoryEntityRepository;
    private final CategoryMapper categoryMapper;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        List<CategoryEntity> categoryEntities = categoryEntityRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntities) {
            CategoryDto categoryDto = categoryMapper.toDto(categoryEntity);
            categoryDtos.add(categoryDto);
        }

        return categoryDtos;
    }

    @Transactional(readOnly = true)
    public CategoryEntity findById(Long id) {
        return categoryEntityRepository.findById(id).orElseThrow(() -> new ApiError.NotFound("category-not-found"));
    }

    @Transactional(readOnly = true)
    public List<CategoryEntity> findByIds(List<Long> ids) {
        return categoryEntityRepository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long id) throws ResponseStatusException {
        var category = this.findById(id);
        return categoryMapper.toDto(category);
    }

    /**
     * Make new category by the admin
     *
     * @param requestDto
     * @return CategoryDto details
     */
    @Transactional
    public CategoryDto createCategory(AddCategoryRequestDto requestDto, UserEntity requestedUser) {
        userService.assertUserIsAdmin(requestedUser);
        var category = this.saveCategory(
                CategoryEntity.builder()
                        .title(requestDto.title)
                        .slug(requestDto.slug)
                        .parent(requestDto.parentCategoryId != null ? this.findById(requestDto.parentCategoryId) : null)
                        .build()
        );
        logger.info("New category created with id: {}", category.getId());
        return categoryMapper.toDto(category);
    }

    /**
     * Update the category details by the admin
     *
     * @param id
     * @param requestDto
     * @return CategoryDto wtih the new details
     * @throws ResponseStatusException
     */
    @Transactional
    public CategoryDto updateCategory(Long id, UpdateCategoryRequestDto requestDto, UserEntity requestedUser) throws ResponseStatusException {
        userService.assertUserIsAdmin(requestedUser);
        var category = this.findById(id);
        category.setTitle(requestDto.title);
        category.setSlug(requestDto.slug);
        category.setParent(requestDto.parentCategoryId != null ? this.findById(requestDto.parentCategoryId) : null);
        this.saveCategory(category);
        logger.info("category updated with id: {}", category.getId());
        return categoryMapper.toDto(category);
    }

    /**
     * Delete the category by the admin with id
     *
     * @param id
     * @throws ResponseStatusException
     */
    @Transactional
    public void deleteCategory(Long id, UserEntity requestedUser) throws ResponseStatusException {
        userService.assertUserIsAdmin(requestedUser);
        var category = this.findById(id);
        logger.info("category deleted with id: {}", category.getId());
        categoryEntityRepository.delete(category);
    }

    @Transactional
    public CategoryEntity saveCategory(CategoryEntity category) {
        return categoryEntityRepository.save(category);
    }

}
