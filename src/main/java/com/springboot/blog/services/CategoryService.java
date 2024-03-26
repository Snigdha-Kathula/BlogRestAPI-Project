package com.springboot.blog.services;

import com.springboot.blog.dtos.CategoryDto;
import com.springboot.blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.models.Category;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto getCategory(Long categoryId) throws ResourceNotFoundException;
    List<Category> getAllCategories();
    Category updateCategory(CategoryDto categoryDto, Long categoryId) throws ResourceNotFoundException;
    void deleteCategory(Long categoryId) throws ResourceNotFoundException;
}
