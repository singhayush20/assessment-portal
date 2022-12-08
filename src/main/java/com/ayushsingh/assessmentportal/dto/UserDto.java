package com.ayushsingh.assessmentportal.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

// @Data
public class UserDto {
    
    private Long userId;
    @NotBlank(message = "Username cannot be empty")
    @Size(min=5,message="username must be greater than 4 characters")
    private String user_name;
    @NotBlank(message = "Password cannot be empty")
    @Size(min=6,message = "Password must be atleast 6 characters long")
    private String password;
    private String lastName;
    @NotBlank(message = "first name cannot be empty")
   
    private String firstName;
    @NotBlank(message = "email cannot be empty")
    @Email
    private String email;
    @Size(min=10,message = "phone number length should be 10")
    private String phone;
    private String enabled;
    private String profile;
    
    private List<RoleDto> roles=new ArrayList<>();
    private List<CategoryDto> enrollCategories=new ArrayList<>();

    public List<CategoryDto> getEnrollCategories() {
        return enrollCategories;
    }

    public void setEnrollCategories(List<CategoryDto> enrollCategories) {
        this.enrollCategories = enrollCategories;
    }

    @JsonIgnore
    private List<QuizDto> createdQuizzes=new ArrayList<>();

    @JsonIgnore
    private List<CategoryDto> createdCategories=new ArrayList<>();
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

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    public List<QuizDto> getCreatedQuizzes() {
        return createdQuizzes;
    }

    public void setCreatedQuizzes(List<QuizDto> createdQuizzes) {
        this.createdQuizzes = createdQuizzes;
    }

    public List<CategoryDto> getCreatedCategories() {
        return createdCategories;
    }

    public void setCreatedCategories(List<CategoryDto> createdCategories) {
        this.createdCategories = createdCategories;
    }


   

    
}
