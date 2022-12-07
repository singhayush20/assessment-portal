package com.ayushsingh.assessmentportal.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class QuizHistoryKey implements Serializable {
    @Column(name="userid")
    private Long userId;

    @Column(name="quiz_id")
    private Long quizId;

    

    public QuizHistoryKey() {
    }

    public QuizHistoryKey(Long userId, Long quizId) {
        this.userId = userId;
        this.quizId = quizId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    
}
