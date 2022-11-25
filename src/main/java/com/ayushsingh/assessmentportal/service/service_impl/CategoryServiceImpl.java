package com.ayushsingh.assessmentportal.service.service_impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayushsingh.assessmentportal.dto.CategoryDto;
import com.ayushsingh.assessmentportal.dto.QuizDto;
import com.ayushsingh.assessmentportal.exceptions.DuplicateResourceException;
import com.ayushsingh.assessmentportal.exceptions.ResourceNotFoundException;
import com.ayushsingh.assessmentportal.model.Category;
import com.ayushsingh.assessmentportal.model.Quiz;
import com.ayushsingh.assessmentportal.model.User;
import com.ayushsingh.assessmentportal.repository.CategoryRepository;
import com.ayushsingh.assessmentportal.repository.UserRepository;
import com.ayushsingh.assessmentportal.service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Override
    public CategoryDto addCategory(CategoryDto categoryDto,Long adminid) {
        Category category=this.dtoToCategory(categoryDto);
        Category temp=this.categoryRepository.findByTitle(categoryDto.getTitle());
        if(temp==null){
            User user=this.userRepository.findById(adminid).get();
            //do this manually because we are using @JsonIgnore on the adminUser of
            //the category dto, it will prevent deserialization from JSON and thus the 
            //admin user will be set to null
            category.setAdminUser(user);
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
            category=this.categoryRepository.save(category);
            return this.categoryToDto(category);
        }
        else{
            throw new ResourceNotFoundException("Category", "category id",categoryId);
        }

    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories=this.categoryRepository.findAll();
        List<CategoryDto> categoryDtos=new ArrayList<>();
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
        this.categoryRepository.deleteById(Long.parseLong(categoryId));
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
    @Override
    public List<QuizDto> getQuizzes(Long categoryId){
        Category category=categoryRepository.findById(categoryId).get();
        List<Quiz> quizList=category.getQuizzes();
        List<QuizDto> quizzes=new ArrayList<>();
        for(Quiz quiz: quizList){
            quizzes.add(this.quizToDto(quiz));
        }
        return quizzes;
    }
    private QuizDto quizToDto(Quiz quiz){
        return this.modelMapper.map(quiz, QuizDto.class);
    }
    // private Quiz dtoToQuiz(QuizDto quizDto){
    //     return this.modelMapper.map(quizDto,Quiz.class);
    // }

    @Override
    public List<CategoryDto> getCategoriesByAdmin(Long adminId) {
        User user=this.userRepository.findById(adminId).get();
        List<Category> categories=user.getCreatedCategories();
        List<CategoryDto> categoryDtos=new ArrayList<>();
        for(Category category: categories){
            categoryDtos.add(this.categoryToDto(category));
        }
        return categoryDtos;
    }

    
}
