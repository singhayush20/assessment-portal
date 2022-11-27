package com.ayushsingh.assessmentportal.dto;

import javax.validation.constraints.NotBlank;


import lombok.Data;

@Data
public class QuestionDto {
    
    private Long questionId;
    @NotBlank(message = "Question descp cannot be empty")
    private String content;
    private String image;

    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;
    private QuizDto quiz;
}
