package com.ayushsingh.assessmentportal.service;

import java.util.Set;

import com.ayushsingh.assessmentportal.dto.QuizDto;

public interface QuizService {
    
    public QuizDto addQuiz(QuizDto quizDto);

    public QuizDto updateQuiz(QuizDto quizDto,String quizId);

    public Set<QuizDto> getQuizzes();

    public QuizDto getQuizById(String quizId);

    public void deleteQuiz(String quizId);
}
