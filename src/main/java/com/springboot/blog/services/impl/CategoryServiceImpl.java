package com.springboot.blog.services.impl;

import com.springboot.blog.dtos.CategoryDto;
import com.springboot.blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.models.Category;
import com.springboot.blog.respositories.CategoryRepository;
import com.springboot.blog.services.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.lang.module.ResolutionException;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category =new Category();
        if(categoryDto.getName()!=null){
            category.setName(categoryDto.getName());
        }
        if(categoryDto.getDescription()!=null){
            category.setDescription(categoryDto.getDescription());
        }
        CategoryDto categoryDto1 = new CategoryDto();
        categoryDto1.setName(category.getName());
        categoryDto1.setId(category.getId());
        categoryDto1.setDescription(category.getDescription());
        categoryDto1.setId(category.getId());
        categoryRepository.save(category);
        return categoryDto1;
    }

    @Override
    public CategoryDto getCategory(Long categoryId) throws ResourceNotFoundException {
         Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
        CategoryDto categoryDto1 = new CategoryDto();
        categoryDto1.setName(category.getName());
        categoryDto1.setDescription(category.getDescription());
        categoryDto1.setId(category.getId());

        categoryRepository.save(category);
        return categoryDto1;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @Override
    public Category updateCategory(CategoryDto categoryDto, Long categoryId) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
        if(categoryDto.getDescription()!=null){
            category.setDescription(categoryDto.getDescription());
        }
        if(categoryDto.getName()!=null){
            category.setName(categoryDto.getName());
        }
        if(categoryDto.getId()!=null){
            category.setId(categoryDto.getId());
        }
        categoryRepository.save(category);

        return category;
    }

    @Override
    public void deleteCategory(Long categoryId) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
        categoryRepository.delete(category);

    }
}
