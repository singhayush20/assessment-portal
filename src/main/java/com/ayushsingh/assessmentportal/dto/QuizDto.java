package com.ayushsingh.assessmentportal.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.ayushsingh.assessmentportal.model.Question;

import lombok.Data;

@Data
public class QuizDto {
    private Long quizId;
    @NotBlank(message = "Quiz Title cannot be empty")
    private String title;
    private String description;
    @NotBlank(message = "Please give max marks for this quiz")
    private String maxMarks;
    @NotBlank(message = "Please give number of questions for the quiz")
    private String numberOfQuestions;
    @NotBlank(message = "Active field cannot be empty")
    private boolean active=false;
    private CategoryDto category;
    private Set<Question> questions=new HashSet<>();
}
