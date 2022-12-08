package com.ayushsingh.assessmentportal.service;

import java.util.List;

import com.ayushsingh.assessmentportal.dto.QuizHistoryDto;

public interface QuizHistoryService {
    List<QuizHistoryDto> getQuizzesByUserId(Long userId);
    List<QuizHistoryDto> getAllUsersForQuiz(Long quizId);
    QuizHistoryDto saveNewRecord(QuizHistoryDto newRecord,Long userid,Long quizid);
    boolean checkIfQuizIsAttempted(Long quizId, Long userId);
}
