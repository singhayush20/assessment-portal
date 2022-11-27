package com.ayushsingh.assessmentportal.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.assessmentportal.model.Question;
import com.ayushsingh.assessmentportal.model.Quiz;

public interface QuestionRepository extends JpaRepository<Question,Long>{

    Set<Question> findByQuiz(Quiz dtoToQuiz);

    // List<Question> findByQuiz(Quiz quiz);
    
}
