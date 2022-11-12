package com.ayushsingh.assessmentportal.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JsonIgnore
    private List<QuestionDto> questions=new ArrayList<>();
}
