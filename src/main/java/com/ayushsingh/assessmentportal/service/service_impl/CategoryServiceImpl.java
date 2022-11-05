package com.ayushsingh.assessmentportal.service.service_impl;


import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayushsingh.assessmentportal.dto.CategoryDto;
import com.ayushsingh.assessmentportal.exceptions.DuplicateResourceException;
import com.ayushsingh.assessmentportal.exceptions.ResourceNotFoundException;
import com.ayushsingh.assessmentportal.model.Category;
import com.ayushsingh.assessmentportal.repository.CategoryRepository;
import com.ayushsingh.assessmentportal.service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category=this.dtoToCategory(categoryDto);
        Category temp=this.categoryRepository.findByTitle(categoryDto.getTitle());
        if(temp==null){
            category=this.categoryRepository.save(category);

        }
        else{
            throw new DuplicateResourceException("category", "title", categoryDto.getTitle());
        }
         
        
        return this.categoryToDto(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto,String categoryId) {
        Optional<Category> local=this.categoryRepository.findById(Long.parseLong(categoryId));
        if(local.isPresent()){
            Category category=local.get();
            category.setTitle(categoryDto.getTitle());
            category.setDescription(categoryDto.getDescription());
            return this.categoryToDto(category);
        }
        else{
            throw new ResourceNotFoundException("Category", "category id",categoryId);
        }

    }

    @Override
    public Set<CategoryDto> getCategories() {
        List<Category> categories=this.categoryRepository.findAll();
        Set<CategoryDto> categoryDtos=new LinkedHashSet<>();
        for(Category category: categories){
            categoryDtos.add(this.categoryToDto(category));
        }
        return categoryDtos;
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {
       Optional<Category> category=this.categoryRepository.findById(Long.parseLong(categoryId));
       if(category.isPresent()){
        return this.categoryToDto(category.get());
       }
       else
       throw new ResourceNotFoundException("Category", "category id", categoryId);
    }

    @Override
    public void deleteCategory(String categoryId) {
        Optional<Category> category=this.categoryRepository.findById(Long.parseLong(categoryId));
       if(category.isPresent()){
        this.deleteCategory(categoryId);
       }
       else
       throw new ResourceNotFoundException("Category", "category id", categoryId);
        
    }

    private Category dtoToCategory(CategoryDto categoryDto) {
        return this.modelMapper.map(categoryDto, Category.class);
    }

    public CategoryDto categoryToDto(Category category) {
        CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }
    
}
