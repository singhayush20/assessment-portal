package com.ayushsingh.assessmentportal.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;


@Entity
@Data
@Table(name="question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "question_id")
    private Long questionId;
    @Column(name="question_descp",nullable = false,length = 5000)
    private String content;
    @Column(name="question_image")
    private String image;
    
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    private String answer;
    @ManyToOne(fetch = FetchType.EAGER)
    private Quiz quiz;


    //transient means that the field will not be stored
    //in the database
    @Transient
    private String submittedAnswer;
}

