package com.ayushsingh.assessmentportal.model;
import java.util.ArrayList;
import java.util.List;

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
@Table(name="category")
@Data
public class Category {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="category_id")
    private Long categoryId;
    @Column(name="title",nullable = false,unique = true)
    private String title;
    @Column(name="descprition",nullable = false)
    private String description;

    @OneToMany(mappedBy = "category",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    // @JsonIgnore
    private List<Quiz> quizzes=new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private User adminUser;
    
    public void deleteQuiz(Quiz quiz) {
        System.out.println("Category: removing quiz from quiz list for category: "+categoryId);
        this.quizzes.remove(quiz);
        this.adminUser=null;
    }

}
