package com.springboot.blog.controllers;

import com.springboot.blog.dtos.CategoryDto;
import com.springboot.blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.models.Category;
import com.springboot.blog.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(
        name = "CRUD REST API's for Category Resource"
)
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bear Authentication"

    )
    @Operation(
            summary = "Create Category Rest API",
            description = "Create Category Rest API is used to create a particular category"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created"
    )
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto savedCategory = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);

    }
    @GetMapping("{id}")
    @Operation(
            summary = "Get Category Rest API",
            description = "Get Category By Id Rest API is used to get a particular category by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Successfully"
    )
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Long categoryId) throws ResourceNotFoundException {
        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(categoryDto);

    }
    @GetMapping
    @Operation(
            summary = "Get All Categories Rest API",
            description = "Get All Categories Rest API is used to fetch all categories from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Successfully"
    )
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bear Authentication"

    )
    @Operation(
            summary = "Update Category Rest API",
            description = "Update Category By Id Rest API is used to update a particular category by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Successfully"
    )
    public ResponseEntity<Category> updateCategoryById(@PathVariable Long id, @RequestBody CategoryDto categoryDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto, id));
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bear Authentication"

    )
    @Operation(
            summary = "Delete Category Rest API",
            description = "Delete Category By Id Rest API is used to delete a particular category by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Successfully"
    )
    public ResponseEntity<String> deleteCategoryById(@PathVariable("id") Long categoryId) throws ResourceNotFoundException {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category deleted Successfully");
    }
}
