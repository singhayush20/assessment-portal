package com.ayushsingh.assessmentportal.service;

import java.util.Set;

import com.ayushsingh.assessmentportal.dto.CategoryDto;

public interface CategoryService {
    
    public CategoryDto addCategory(CategoryDto categoryDto);

    public CategoryDto updateCategory(CategoryDto categoryDto,String categoryId);
    public Set<CategoryDto> getCategories();
    
    public CategoryDto getCategoryById(String categoryId);

    public void deleteCategory(String categoryId);
}
