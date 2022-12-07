package com.ayushsingh.assessmentportal.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.ayushsingh.assessmentportal.model.QuizHistory;
import com.ayushsingh.assessmentportal.model.QuizHistoryKey;

public interface QuizHistoryRepository extends JpaRepository<QuizHistory,QuizHistoryKey> {
    List<QuizHistory> findAllById_UserId(Long userId);
    List<QuizHistory> findAllById_QuizId(Long quizId);
}
