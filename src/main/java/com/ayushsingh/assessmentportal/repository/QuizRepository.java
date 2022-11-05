package com.ayushsingh.assessmentportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.assessmentportal.model.Quiz;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
    
}
