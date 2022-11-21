package com.ayushsingh.assessmentportal.service.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ayushsingh.assessmentportal.exceptions.ResourceNotFoundException;
import com.ayushsingh.assessmentportal.model.User;
import com.ayushsingh.assessmentportal.repository.UserRepository;
/*
 * This class is part of JWT Authentication
 * the method loadUserByUsername is used to load the user
 * from the database
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    private final String CLASS_NAME=UserDetailsServiceImpl.class.getSimpleName();
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> userList=this.userRepository.findByEmail(username);
        // User user=this.userRepository.findByUsername(username);
        if(userList.size()==0){
            System.out.println(CLASS_NAME+" User not found");
            throw new ResourceNotFoundException("User", "email", username);
        }
        return userList.get(0);
    }
    
}
