package com.ayushsingh.assessmentportal.service;

import java.util.Set;

import com.ayushsingh.assessmentportal.dto.QuestionDto;
import com.ayushsingh.assessmentportal.dto.QuizDto;
import com.ayushsingh.assessmentportal.model.Question;

public interface QuestionService {
    
    public QuestionDto addQuestion(QuestionDto questionDto);

    public QuestionDto updaQuestion(QuestionDto questionDto);

    public Set<QuestionDto> getQuestions();
    
    public QuestionDto getQuestion(String questionId);

    public Set<QuestionDto> getQuestionsOfQuiz(QuizDto quizDto);
}
