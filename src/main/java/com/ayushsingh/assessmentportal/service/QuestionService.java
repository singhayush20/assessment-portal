package com.ayushsingh.assessmentportal.service;

import java.util.List;
import java.util.Set;

import com.ayushsingh.assessmentportal.dto.QuestionDto;

public interface QuestionService {
    
    public QuestionDto addQuestion(QuestionDto questionDto);

    public QuestionDto updaQuestion(QuestionDto questionDto);

    public List<QuestionDto> getQuestions();
    
    public QuestionDto getQuestion(String questionId);

    public List<QuestionDto> getQuestionsOfQuiz(Long quizId);

    public void deleteQuestion(String questionId);
}
