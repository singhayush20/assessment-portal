package com.ayushsingh.assessmentportal.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;


// @Data
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
    @NotBlank(message="Time cannot be empty")
    private int time;
    private CategoryDto category;
    @JsonIgnore
    private List<QuestionDto> questions=new ArrayList<>();
    @JsonIgnore
    private UserDto adminUser;
    
    public Long getQuizId() {
        return quizId;
    }
    public void setQuizId(Long quizId) {
        this.quizId = quizId;
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
    public String getMaxMarks() {
        return maxMarks;
    }
    public void setMaxMarks(String maxMarks) {
        this.maxMarks = maxMarks;
    }
    public String getNumberOfQuestions() {
        return numberOfQuestions;
    }
    public void setNumberOfQuestions(String numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public CategoryDto getCategory() {
        return category;
    }
    public void setCategory(CategoryDto category) {
        this.category = category;
    }
    public List<QuestionDto> getQuestions() {
        return questions;
    }
    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }
    public UserDto getAdminUser() {
        return adminUser;
    }
    public void setAdminUser(UserDto adminUser) {
        this.adminUser = adminUser;
    }
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
    

}
