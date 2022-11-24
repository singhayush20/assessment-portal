package com.ayushsingh.assessmentportal.service;


import java.util.List;

import com.ayushsingh.assessmentportal.dto.QuizDto;
import com.ayushsingh.assessmentportal.dto.UserDto;
import com.ayushsingh.assessmentportal.model.User;

public interface UserService  {
    
    //creating user
    public UserDto createUser(UserDto user);
    public UserDto findByEmail(String email);
    // creating user
	// public User createUser(User user,List<UserRole> userRoles)throws Exception;//UserRole contain roles
    
    public UserDto findByUsername(String username);

    // public void deleteUserByUsername(String username);

    public void deleteUserbyId(String id);

    public UserDto updateUser(UserDto userDto,String id);

    UserDto registerNewUser(UserDto userDto);

    User getUserByUsername(String username);

    UserDto registerAdminUser(UserDto userDto,String key);

    //return the quizzes created by this user
    List<QuizDto> getQuizzesByAdminAndCategory(Long adminid,Long categoryId);
}
