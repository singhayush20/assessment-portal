package com.ayushsingh.assessmentportal.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name="quiz_record")
public class QuizHistory { 

    
    public QuizHistory() {
    }

    @EmbeddedId
    private QuizHistoryKey id;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @MapsId("quizId")
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Column(name = "no_of_questions", nullable = false)
    private int noOfQuestions;

    @Column(name = "questions_attempted", nullable = false)
    private int questionsAttempted;

    @Column(name = "correct_questions", nullable = false)
    private int correctQuestions;

    @Column(name = "maxmarks", nullable = false)
    private int maxMarks;

    @Column(name = "marks_obtained", nullable = false)
    private int marksObtained;

    public QuizHistoryKey getId() {
        return id;
    }

    public void setId(QuizHistoryKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public int getNoOfQuestions() {
        return noOfQuestions;
    }

    public void setNoOfQuestions(int noOfQuestions) {
        this.noOfQuestions = noOfQuestions;
    }

    public int getQuestionsAttempted() {
        return questionsAttempted;
    }

    public void setQuestionsAttempted(int questionsAttempted) {
        this.questionsAttempted = questionsAttempted;
    }

    public int getCorrectQuestions() {
        return correctQuestions;
    }

    public void setCorrectQuestions(int correctQuestions) {
        this.correctQuestions = correctQuestions;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(int maxMarks) {
        this.maxMarks = maxMarks;
    }

    public int getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(int marksObtained) {
        this.marksObtained = marksObtained;
    }

}
