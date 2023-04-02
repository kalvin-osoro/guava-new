package com.ecomerce.guava.service.impl;

import com.ecomerce.guava.dto.CategoryDto;
import com.ecomerce.guava.exceptions.ResourceNotFoundException;
import com.ecomerce.guava.files.FileStorageService;
import com.ecomerce.guava.model.Category;
import com.ecomerce.guava.repository.CategoryRepo;
import com.ecomerce.guava.utils.FileUploadUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {
@Autowired
   CategoryRepo categoryRepository;

//    @Autowired
//    public CategoryService(CategoryRepository categoryRepository) {
//        this.categoryRepository = categoryRepository;
//    }

//    public Category createCategory(CategoryDto categoryDto, MultipartFile image) throws IOException {
//    public Category createCategory(String categoryName, String description, MultipartFile image) throws IOException {
//        // create category entity from DTO
//        CategoryDto categoryDto = new CategoryDto();
//        Category category = new Category(categoryDto.getCategoryName(), categoryDto.getDescription());
//
//        // save image to file system
//        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
//        String uploadDir = "category-images/";
//        FileUploadUtil.saveFile(uploadDir, fileName, image);
//        String imagePath = uploadDir + fileName;
//        category.setImageUrl(imagePath);
//
//        // save category to database
//        return categoryRepository.save(category);
//    }
public Category createCategory(String categoryName, String description, MultipartFile image) throws IOException {
    // create category entity from parameters
    Category category = new Category(categoryName, description);

    // save image to file system
    String fileName = StringUtils.cleanPath(image.getOriginalFilename());
    String uploadDir = "category-images/";
    FileUploadUtil.saveFile(uploadDir, fileName, image);
    String imagePath = uploadDir + fileName;
    category.setImageUrl(imagePath);

    // save category to database
    return categoryRepository.save(category);
}


    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Integer id) {
        return categoryRepository.findById(id);
    }
}

