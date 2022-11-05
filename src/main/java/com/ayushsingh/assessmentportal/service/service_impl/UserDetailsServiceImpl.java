package com.ayushsingh.assessmentportal.service.service_impl;

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
        
        User user=this.userRepository.findByUsername(username);
        if(user==null){
            System.out.println(CLASS_NAME+" User not found");
            throw new ResourceNotFoundException("User", "username", username);
        }
        return user;
    }
    
}
