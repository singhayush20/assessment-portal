package com.ayushsingh.assessmentportal.model;

import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.PreRemove;
import javax.persistence.Table;



@Entity
@Table(name="quiz")
// @Getter
// @Setter
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
   @Column(name="time",nullable = false)
   private int time;

  
   @OneToMany(mappedBy = "quiz",fetch = FetchType.LAZY/*Data is obtained when getter is called */,cascade = CascadeType.ALL)
//    @JsonIgnore //use json ignore so that we do not fetch questions
   private List<Question> questions=new ArrayList<>();

   @ManyToOne(fetch = FetchType.EAGER)
   private User adminUser;

   //To store the users who attempted the quiz
   @OneToMany(mappedBy = "quiz",fetch = FetchType.LAZY)
   private List<QuizHistory> quizHistory;

   @PreRemove
   public void removeCategory(){
      System.out.println("Quiz: removing category for quizId: "+quizId);
      this.category=null;
      System.out.println("Quiz: removing the admin user for quizid: "+quizId);
      this.adminUser=null;
   }
   public Long getQuizId() {
      return quizId;
   }
   public void setQuizId(Long quizId) {
      this.quizId = quizId;
   }
   public String getTitle() {
      return title;
   }
   public void setTitle(String title) {
      this.title = title;
   }
   public String getDescription() {
      return description;
   }
   public void setDescription(String description) {
      this.description = description;
   }
   public String getMaxMarks() {
      return maxMarks;
   }
   public void setMaxMarks(String maxMarks) {
      this.maxMarks = maxMarks;
   }
   public String getNumberOfQuestions() {
      return numberOfQuestions;
   }
   public void setNumberOfQuestions(String numberOfQuestions) {
      this.numberOfQuestions = numberOfQuestions;
   }
   public boolean isActive() {
      return active;
   }
   public void setActive(boolean active) {
      this.active = active;
   }
   public Category getCategory() {
      return category;
   }
   public void setCategory(Category category) {
      this.category = category;
   }
   public List<Question> getQuestions() {
      return questions;
   }
   public void setQuestions(List<Question> questions) {
      this.questions = questions;
   }
   public User getAdminUser() {
      return adminUser;
   }
   public void setAdminUser(User adminUser) {
      this.adminUser = adminUser;
   }
   public int getTime() {
      return time;
   }
   public void setTime(int time) {
      this.time = time;
   }
   public List<QuizHistory> getQuizHistory() {
      return quizHistory;
   }
   public void setQuizHistory(List<QuizHistory> quizHistory) {
      this.quizHistory = quizHistory;
   }
 
   
   
}
