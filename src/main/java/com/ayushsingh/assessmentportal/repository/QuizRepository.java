package com.ayushsingh.assessmentportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.assessmentportal.model.Category;
import com.ayushsingh.assessmentportal.model.Quiz;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
    

    public List<Quiz> findByCategoryAndActive(Category c, Boolean b);
}
