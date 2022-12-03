package com.ayushsingh.assessmentportal.service;

import java.util.List;

import com.ayushsingh.assessmentportal.dto.QuizDto;

public interface QuizService {
    
    public QuizDto addQuiz(QuizDto quizDto,Long userid);

    public QuizDto updateQuiz(QuizDto quizDto,String quizId);

    public List<QuizDto> getQuizzes();

    public QuizDto getQuizById(String quizId);

    public void deleteQuiz(String quizId);

    public List<QuizDto> getActiveQuizzesByCategory(Long categoryId);
}
