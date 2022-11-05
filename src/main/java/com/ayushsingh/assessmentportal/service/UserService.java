package com.ayushsingh.assessmentportal.service;


import com.ayushsingh.assessmentportal.dto.UserDto;
import com.ayushsingh.assessmentportal.model.User;

public interface UserService  {
    
    //creating user
    public UserDto createUser(UserDto user);
    // creating user
	// public User createUser(User user,List<UserRole> userRoles)throws Exception;//UserRole contain roles
    
    public UserDto findByUsername(String username);

    // public void deleteUserByUsername(String username);

    public void deleteUserbyId(String id);

    public UserDto updateUser(UserDto userDto,String id);

    UserDto registerNewUser(UserDto userDto);

    User getUserByUsername(String username);

    UserDto registerAdminUser(UserDto userDto,String key);
}