package com.ecomerce.guava.controller;

import com.ecomerce.guava.common.ApiResponse;
import com.ecomerce.guava.dto.CategoryDto;
import com.ecomerce.guava.model.Category;
import com.ecomerce.guava.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@RestController
//@RequestMapping("/category")
//@CrossOrigin(origins = "http://localhost:8090")
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(value = "/create", consumes = {"application/octet-stream", "multipart/form-data"})
//    public ResponseEntity<CategoryDto> createCategory(@RequestPart("categoryDto") CategoryDto categoryDto,
//                                                      @RequestPart("categoryImage") MultipartFile categoryImage) throws IOException {
    public ResponseEntity<CategoryDto> createCategory(@RequestPart String categoryName, @RequestPart String description,
                                                      @RequestPart("categoryImage") MultipartFile categoryImage) throws IOException {
//        Category createdCategory = categoryService.createCategory(categoryDto, categoryImage);
        Category createdCategory = categoryService.createCategory(categoryName, description, categoryImage);
        CategoryDto createdCategoryDto = new CategoryDto(createdCategory.getId(),
                createdCategory.getCategoryName(), createdCategory.getDescription(), createdCategory.getImageUrl());
        return ResponseEntity.ok().body(createdCategoryDto);
    }

    @GetMapping("list")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDto> categoryDtos = categories.stream().map(category -> new CategoryDto(category.getId(),
                category.getCategoryName(), category.getDescription(), category.getImageUrl())).collect(Collectors.toList());
        return ResponseEntity.ok().body(categoryDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer id) {
        Optional<Category> categoryOptional = categoryService.getCategoryById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            CategoryDto categoryDto = new CategoryDto(category.getId(), category.getCategoryName(),
                    category.getDescription(), category.getImageUrl());
            return ResponseEntity.ok().body(categoryDto);
        }
        return ResponseEntity.notFound().build();
    }
}
