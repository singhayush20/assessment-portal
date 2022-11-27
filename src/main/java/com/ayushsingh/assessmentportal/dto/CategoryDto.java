package com.ayushsingh.assessmentportal.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class CategoryDto {
    private Long categoryId;
    @NotBlank(message = "Category title cannot be empty")
    private String title;
    @NotBlank(message = "Description cannot be empty")
    private String description;
    @JsonIgnore
    private List<QuizDto> quizzes=new ArrayList<>();

    @JsonIgnore
    private UserDto adminUser;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<QuizDto> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<QuizDto> quizzes) {
        this.quizzes = quizzes;
    }

    public UserDto getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(UserDto adminUser) {
        this.adminUser = adminUser;
    }


}
