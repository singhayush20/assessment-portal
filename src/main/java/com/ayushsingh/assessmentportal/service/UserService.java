package com.ayushsingh.assessmentportal.service;


import com.ayushsingh.assessmentportal.dto.UserDto;

public interface UserService  {
    
    //creating user
    public UserDto createUser(UserDto user);
    // creating user
	// public User createUser(User user,List<UserRole> userRoles)throws Exception;//UserRole contain roles
    
    public UserDto findByUsername(String username);

    // public void deleteUserByUsername(String username);

    public void deleteUserbyId(String id);

    public UserDto updateUser(UserDto userDto,String id);

}
