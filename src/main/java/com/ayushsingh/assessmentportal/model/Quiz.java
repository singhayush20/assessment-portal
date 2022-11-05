package com.ayushsingh.assessmentportal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import lombok.Data;

@Entity
@Table(name="quiz")
@Data
public class Quiz {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name="quiz_id")
   private Long quizId; 
   @Column(name="quiz_title",nullable = false)
   private String title;
   @Column(name="quiz_descp")
   private String description;
   @Column(name="max_marks",nullable = false)
   private String maxMarks;
   @Column(name="no_of_ques",nullable = false)
   private String numberOfQuestions;
   @Column(name="is_active",nullable = false)
   private boolean active=false;
    @ManyToOne(fetch = FetchType.EAGER)
   private Category category;

   @OneToMany(mappedBy = "quiz",fetch = FetchType.LAZY/*Data is obtained when getter is called */,cascade = CascadeType.ALL)
//    @JsonIgnore //use json igone so that we do not fetch questions 
   private Set<Question> questions=new HashSet<>();

   
}
