package com.ayushsingh.assessmentportal.model;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;




@Entity
@Table(name = "user") // to give a name of our choice to the table
public class User implements UserDetails /*
                                          * Spring Security uses the UserDetails interface, can also create a separate
                                          * class
                                          */ {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "userid")
        private Long userId;
        @Column(name = "username", nullable = false, unique = true)
        private String user_name;
        @Column(name = "password", nullable = false)
        private String password;
        @Column(name = "lastname")
        private String lastName;
        @Column(name = "firstname", nullable = false)
        private String firstName;

        @Column(name = "email", nullable = false, unique = true)
        private String email;
        @Column(name = "contactno")
        private String phone;
        @Column(name = "isenabled")
        private String enabled = "true"; // by default: enable the user

        private String profile;

        @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
                        CascadeType.REFRESH }/* Save role when user is saved */, fetch = FetchType.EAGER) // each user
                                                                                                          // can have                                                                                              // many roles
        @JoinTable(name = "user_role", // the name of the table which manages this relationship
                        // specify the join columns in that table
                        joinColumns = @JoinColumn(
                                        /*
                                         * name of the join column
                                         * The name of the foreign key column which stores the user id
                                         * The table in which it is found depends upon the context.
                                         */
                                        name = "user",
                                        /*
                                         * The name of the primary key in the
                                         * user table which works as the foreign key in this table
                                         * column.
                                         */
                                        referencedColumnName = "userid"),
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
                                        name = "role",

                                        /*
                                         * The name of the column referenced by this foreign key column. The primary key
                                         * in the role table working as the foreign key in this table
                                         */
                                        referencedColumnName = "roleid"))
        private List<Role> roles = new ArrayList<>();
        
        @OneToMany(mappedBy = "adminUser",cascade = CascadeType.ALL)
        private List<Quiz> createdQuizzes=new ArrayList<>();

        @OneToMany(mappedBy = "adminUser",cascade = CascadeType.ALL)
        private List<Category> createdCategories=new ArrayList<>();


        @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
                CascadeType.REFRESH }, fetch = FetchType.LAZY) 
                //each normal user can be enrolled in many categories
                                                                                                
@JoinTable(name = "enrolled", // the name of the table which manages this relationship
                // specify the join columns in that table
                joinColumns = @JoinColumn(
                                /*
                                 * name of the join column
                                 * The name of the foreign key column which stores the user id
                                 * The table in which it is found depends upon the context.
                                 */
                                name = "user",
                                /*
                                 * The name of the primary key in the
                                 * user table which works as the foreign key in this table
                                 * column.
                                 */
                                referencedColumnName = "userid"),
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
                                name = "category",

                                /*
                                 * The name of the column referenced by this foreign key column. The primary key
                                 * in the role table working as the foreign key in this table
                                 */
                                referencedColumnName = "category_id"))
        Set<Category> enrolledCategories=new HashSet<>();

        //To store the quizzes attempted by user
       @OneToMany(mappedBy="user",fetch = FetchType.LAZY)
      private  List<QuizHistory> quizHistory;
 
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                // create authorites using roles and return
                Set<Authority> set = new HashSet<>();
                for (Role role : roles) {
                        set.add(new Authority(role.getRoleName()));
                }
                return set;
        }

        @Override
        public boolean isAccountNonExpired() {

                return true;
        }

        @Override
        public boolean isAccountNonLocked() {

                return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {

                return true;
        }

        @Override
        public boolean isEnabled() {

                // return the enabled status
                // if the enabled is set to false
                // return false
                // this will disable account
                //check if enabled is null or not- NullPointerException on __toDto conversion
                if (this.enabled!=null&&this.enabled.equalsIgnoreCase(enabled)) {
                        return true;
                } else {

                        return false;
                }
        }
        @PreRemove
        public void removeCategories(){
                this.enrolledCategories=null;
                System.out.println("Categories set to null for user: "+this.userId);
        }

        @Override
        public String getUsername() {
                return email;
        }

        public Long getUserId() {
                return userId;
        }

        public void setUserId(Long userId) {
                this.userId = userId;
        }

        public String getUser_name() {
                return user_name;
        }

        public void setUser_name(String user_name) {
                this.user_name = user_name;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getFirstName() {
                return firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPhone() {
                return phone;
        }

        public void setPhone(String phone) {
                this.phone = phone;
        }

        public String getEnabled() {
                return enabled;
        }

        public void setEnabled(String enabled) {
                this.enabled = enabled;
        }

        public String getProfile() {
                return profile;
        }

        public void setProfile(String profile) {
                this.profile = profile;
        }

        public List<Role> getRoles() {
                return roles;
        }

        public void setRoles(List<Role> roles) {
                this.roles = roles;
        }

        public List<Quiz> getCreatedQuizzes() {
                return createdQuizzes;
        }

        public void setCreatedQuizzes(List<Quiz> createdQuizzes) {
                this.createdQuizzes = createdQuizzes;
        }

        public List<Category> getCreatedCategories() {
                return createdCategories;
        }

        public void setCreatedCategories(List<Category> createdCategories) {
                this.createdCategories = createdCategories;
        }

        public Set<Category> getEnrolledCategories() {
                return enrolledCategories;
        }

        public void setEnrolledCategories(Set<Category> enrolledCategories) {
                this.enrolledCategories = enrolledCategories;
        }

        public List<QuizHistory> getQuizHistory() {
                return quizHistory;
        }

        public void setQuizHistory(List<QuizHistory> quizHistory) {
                this.quizHistory = quizHistory;
        }

        

        

}
