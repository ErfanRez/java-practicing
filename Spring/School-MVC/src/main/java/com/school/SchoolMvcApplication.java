package com.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.school.repository")
@EntityScan("com.school.model")
public class SchoolMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolMvcApplication.class, args);
    }

}
