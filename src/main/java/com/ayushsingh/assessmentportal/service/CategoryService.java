package com.ayushsingh.assessmentportal.service;

import java.util.List;

import com.ayushsingh.assessmentportal.dto.CategoryDto;
import com.ayushsingh.assessmentportal.dto.QuizDto;

public interface CategoryService {
    
    public CategoryDto addCategory(CategoryDto categoryDto,Long adminid);

    public CategoryDto updateCategory(CategoryDto categoryDto,String categoryId);
    public List<CategoryDto> getCategories();
    
    public CategoryDto getCategoryById(String categoryId);

    public void deleteCategory(String categoryId);

    public List<QuizDto> getQuizzes(Long categoryId);
    public List<CategoryDto> getCategoriesByAdmin(Long adminId);
}
