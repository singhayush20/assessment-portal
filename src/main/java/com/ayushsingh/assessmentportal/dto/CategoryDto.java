package com.ayushsingh.assessmentportal.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
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
}
