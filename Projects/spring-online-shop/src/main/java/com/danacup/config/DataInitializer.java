package com.danacup.config;

import com.danacup.entity.CategoryEntity;
import com.danacup.entity.UserEntity;
import com.danacup.repository.CategoryEntityRepository;
import com.danacup.repository.UserEntityRepository;
import com.danacup.enums.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@Slf4j
public class DataInitializer {
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Bean
    CommandLineRunner commandLineRunner(
            UserEntityRepository userRepository,
            CategoryEntityRepository categoryRepository
    ) {
        return args -> {
            // * Initialize Default Users
            UserEntity admin = userRepository.findByUsername("admin")
                    .orElse(UserEntity.builder()
                            .username("admin")
                            .email("admin@example.com")
                            .password(bCryptPasswordEncoder.encode("admin"))
                            .role(UserRole.ADMIN)
                            .build());
            userRepository.save(admin);

            UserEntity testUser = userRepository.findByUsername("test")
                    .orElse(UserEntity.builder()
                            .username("test")
                            .email("test@example.com")
                            .password(bCryptPasswordEncoder.encode("testtest"))
                            .role(UserRole.USER)
                            .build());
            userRepository.save(testUser);

            // * Initialize Default Mobile Categories
            CategoryEntity mobileCategory = categoryRepository.findBySlug("mobile")
                    .orElse(CategoryEntity.builder()
                            .title("موبایل")
                            .slug("mobile")
                            .build());
            categoryRepository.save(mobileCategory);
            CategoryEntity iphoneMobileCategory = categoryRepository.findBySlug("mobile-iphone")
                    .orElse(CategoryEntity.builder()
                            .title("گوشی آیفون")
                            .slug("mobile-iphone")
                            .parent(mobileCategory)
                            .build());
            categoryRepository.save(iphoneMobileCategory);
            CategoryEntity samsungMobileCategory = categoryRepository.findBySlug("mobile-samsung")
                    .orElse(CategoryEntity.builder()
                            .title("گوشی سامسونگ")
                            .slug("mobile-samsung")
                            .parent(mobileCategory)
                            .build());
            categoryRepository.save(samsungMobileCategory);
            CategoryEntity xiaomiMobileCategory = categoryRepository.findBySlug("mobile-xiaomi")
                    .orElse(CategoryEntity.builder()
                            .title("گوشی شیائومی")
                            .slug("mobile-xiaomi")
                            .parent(mobileCategory)
                            .build());
            categoryRepository.save(xiaomiMobileCategory);

            // * Initialize Default Mobile Categories
            CategoryEntity laptopCategory = categoryRepository.findBySlug("laptop")
                    .orElse(CategoryEntity.builder()
                            .title("لپتاپ")
                            .slug("laptop")
                            .build());
            categoryRepository.save(laptopCategory);
            CategoryEntity asusLaptopCategory = categoryRepository.findBySlug("laptop-asus")
                    .orElse(CategoryEntity.builder()
                            .title("لپتاپ ایسوس")
                            .slug("laptop-asus")
                            .parent(laptopCategory)
                            .build());
            categoryRepository.save(asusLaptopCategory);
            CategoryEntity lenovoLaptopCategory = categoryRepository.findBySlug("laptop-lenovo")
                    .orElse(CategoryEntity.builder()
                            .title("لپ تاپ لنوو")
                            .slug("laptop-lenovo")
                            .parent(laptopCategory)
                            .build());
            categoryRepository.save(lenovoLaptopCategory);
            CategoryEntity appleLaptopCategory = categoryRepository.findBySlug("laptop-apple")
                    .orElse(CategoryEntity.builder()
                            .title("لپ تاپ اپل")
                            .slug("laptop-apple")
                            .parent(laptopCategory)
                            .build());
            categoryRepository.save(appleLaptopCategory);

            log.info("data initialized");
        };
    }
}