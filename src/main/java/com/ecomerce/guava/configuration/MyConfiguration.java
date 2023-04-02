package com.ecomerce.guava.configuration;

import com.ecomerce.guava.files.FileStorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {
    @Bean
    public FileStorageService fileStorageService() {
        return new FileStorageService();
    }
}



