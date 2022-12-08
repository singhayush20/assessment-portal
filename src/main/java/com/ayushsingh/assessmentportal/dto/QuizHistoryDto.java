package com.ayushsingh.assessmentportal.dto;

import com.ayushsingh.assessmentportal.model.QuizHistoryKey;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QuizHistoryDto {
    private QuizHistoryKey id;

   
    private UserDto2 user;

    private QuizDto quiz;

    private int noOfQuestions;

    private int questionsAttempted;

    private int correctQuestions;

    private int maxMarks;

    private int marksObtained;

    public QuizHistoryKey getId() {
        return id;
    }

    public void setId(QuizHistoryKey id) {
        this.id = id;
    }
    public UserDto2 getUser() {
        return user;
    }
    @JsonProperty("user")
    public void setUser(UserDto2 user) {
        this.user = user;
    }
    public QuizDto getQuiz() {
        return quiz;
    }
    @JsonProperty("quiz")
    public void setQuiz(QuizDto quiz) {
        this.quiz = quiz;
    }

    public int getNoOfQuestions() {
        return noOfQuestions;
    }

    public void setNoOfQuestions(int noOfQuestions) {
        this.noOfQuestions = noOfQuestions;
    }

    public int getQuestionsAttempted() {
        return questionsAttempted;
    }

    public void setQuestionsAttempted(int questionsAttempted) {
        this.questionsAttempted = questionsAttempted;
    }

    public int getCorrectQuestions() {
        return correctQuestions;
    }

    public void setCorrectQuestions(int correctQuestions) {
        this.correctQuestions = correctQuestions;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(int maxMarks) {
        this.maxMarks = maxMarks;
    }

    public int getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(int marksObtained) {
        this.marksObtained = marksObtained;
    }

    @Override
    public String toString() {
        return "QuizHistoryDto [id=" + id + ", user=" + user + ", quiz=" + quiz + ", noOfQuestions=" + noOfQuestions
                + ", questionsAttempted=" + questionsAttempted + ", correctQuestions=" + correctQuestions
                + ", maxMarks=" + maxMarks + ", marksObtained=" + marksObtained + "]";
    }

    
}
