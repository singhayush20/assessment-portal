package com.ayushsingh.assessmentportal.model;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="category")
public class Category {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="category_id")
    private Long categoryId;
    @Column(name="title",nullable = false,unique = true)
    private String title;
    @Column(name="descprition",nullable = false)
    private String description;

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY /*Make it LAZY */,cascade = CascadeType.ALL)
    //We have set Category in Quiz entity with FetchType EAGER. If we make the quiz here as FETCH TYPE EAGER,
    //then because a kind of cycle is created, we will not be able to delete quiz, if we delete quiz, hibernate will
    //again save it
        // @JsonIgnore
    private List<Quiz> quizzes=new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    // @ManyToOne
    private User adminUser;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
        CascadeType.REFRESH}/* Save role when user is saved */, fetch = FetchType.LAZY) 
        //each normal user can be enrolled in many categories
                                                                                        
@JoinTable(name = "enrolled", // the name of the table which manages this relationship
        // specify the join columns in that table
        joinColumns = @JoinColumn(
                        /*
                         * name of the join column
                         * The name of the foreign key column which stores the user id
                         * The table in which it is found depends upon the context.
                         */
                        name = "category",
                        /*
                         * The name of the primary key in the
                         * user table which works as the foreign key in this table
                         * column.
                         */
                        referencedColumnName = "category_id"),
        /*
         * The foreign key columns of the join table which reference the primary table
         * of the entity that does not own the association. (I.e. the inverse side of
         * the association).
         */
        inverseJoinColumns = @JoinColumn(
                        /*
                         * The name of the foreign key column. The table in which it is found depends
                         * upon the context. The column which keeps the role id in this table
                         */
                        name = "user",

                        /*
                         * The name of the column referenced by this foreign key column. The primary key
                         * in the role table working as the foreign key in this table
                         */
                        referencedColumnName = "userid"))
    private Set<User> enrolledStudents=new HashSet<>();
    
    public void deleteQuiz(Quiz quiz) {
        System.out.println("Category: removing quiz from quiz list for category: "+categoryId);
        this.quizzes.remove(quiz);
        this.adminUser=null;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public User getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(User adminUser) {
        this.adminUser = adminUser;
    }

    public Set<User> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(Set<User> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
        

}
