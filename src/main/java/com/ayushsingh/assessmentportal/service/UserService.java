package com.ayushsingh.assessmentportal.service;


import java.util.List;

import com.ayushsingh.assessmentportal.dto.CategoryDto;
import com.ayushsingh.assessmentportal.dto.QuizDto;
import com.ayushsingh.assessmentportal.dto.UserDto;
import com.ayushsingh.assessmentportal.model.User;

public interface UserService  {
    
    //creating user

    public UserDto findByEmail(String email);

    


    public void deleteUserbyId(String id);

    public UserDto updateUser(UserDto userDto,String id);

    UserDto registerNewUser(UserDto userDto);

    User getUserByUsername(String username);

    UserDto registerAdminUser(UserDto userDto,String key);

    //return the quizzes created by this user
    List<QuizDto> getQuizzesByAdminAndCategory(Long adminid,Long categoryId);

    List<CategoryDto> getEnrolledCategories(Long userid);

    void addEnrolledCategory(Long userid, Long categoryid);
}
