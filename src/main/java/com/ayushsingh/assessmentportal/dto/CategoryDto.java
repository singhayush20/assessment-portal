package com.ayushsingh.assessmentportal.dto;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CategoryDto {
    private Long categoryId;
    @NotBlank(message = "Category title cannot be empty")
    private String title;
    @NotBlank(message = "Description cannot be empty")
    private String description;
    
    private Set<QuizDto> quizzes=new LinkedHashSet<>();

}
