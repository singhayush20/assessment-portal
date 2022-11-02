package com.ayushsingh.assessmentportal.service.service_impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayushsingh.assessmentportal.constants.AppConstants;
import com.ayushsingh.assessmentportal.dto.UserDto;
import com.ayushsingh.assessmentportal.exceptions.DuplicateResourceException;
import com.ayushsingh.assessmentportal.exceptions.ResourceNotFoundException;
import com.ayushsingh.assessmentportal.model.Role;
import com.ayushsingh.assessmentportal.model.User;
import com.ayushsingh.assessmentportal.repository.RoleRepository;
import com.ayushsingh.assessmentportal.repository.UserRepository;
import com.ayushsingh.assessmentportal.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    private final String CLASS_NAME=UserServiceImpl.class.getName();
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;
    //create user
    @Override
    public UserDto createUser(UserDto userDto) {
        
        //check if an user already exists
        User user=this.dtoToUser(userDto);
        User local=this.userRepository.findByUsername(user.getUsername());
        if(local!=null){
            //i.e., the user with this username is already present
            System.out.println(CLASS_NAME+" is user present: "+local.toString());

            System.out.println("User already exists");
            //throw an exception which will be handled by any
            //method which invokes this method
            throw new DuplicateResourceException("User", "username", user.getUsername());
        }
        else{
            System.out.println(CLASS_NAME+" user not present with these details");
            try{
                Role role=this.roleRepository.findById(AppConstants.NORMAL_ROLE_ID).get();
               user.getRoles().add(role);
                   //save the users and the user roles will 
                   //be saved automatically because of CacadeType.ALL
                   //on the UserRoles entities
                   local=this.userRepository.save(user);
            }
            catch(Exception e){
                throw e;
            }
            //create user
        
        }
        return this.usertoDto(local);
    }

    
    
   

    // @Override
    // public void deleteUserByUsername(String username) {
    //     User local=this.userRepository.findByUsername(username);
    //     if(local==null){
    //         throw new ResourceNotFoundException("User", "username", username);
    //     }
    //     else{
    //         this.userRepository.deleteByUsername(username);
    //     }
    // }






    @Override
    public void deleteUserbyId(String id) {
        Long userid=Long.parseLong(id);
         Optional<User> local=this.userRepository.findById(userid);
         System.out.println("local: "+local);
        if(!local.isPresent()){
            System.out.println("User not found");
            throw new ResourceNotFoundException("User", "userid", id);
        }
        else{
            this.userRepository.deleteById(userid);
        }
        
    }





    @Override
    public UserDto findByUsername(String username)  {
        User user=userRepository.findByUsername(username);
       if(user==null){
        throw new ResourceNotFoundException("User", "username", username);
       }
       return this.usertoDto(user);
    }



    private User dtoToUser(UserDto userDto){
        return this.modelMapper.map(userDto,User.class);
    }
    public UserDto usertoDto(User user) {
        UserDto userDto=this.modelMapper.map(user,UserDto.class);
        return userDto;
    }





    @Override
    public UserDto updateUser(UserDto userDto, String id) {
       User newUser=this.dtoToUser(userDto);
       Long userid=Long.parseLong(id);
       User oldUser=null;
       Optional<User> local=this.userRepository.findById(userid);
       System.out.println("local: "+local);
      if(!local.isPresent()){
          System.out.println("User not found");
          throw new ResourceNotFoundException("User", "userid", id);
      }
      else{
          oldUser=local.get();
          oldUser.setEmail(newUser.getEmail());
          oldUser.setFirstName(newUser.getFirstName());
          oldUser.setLastName(newUser.getLastName());
          oldUser.setPassword(newUser.getPassword());
          oldUser.setPhone(newUser.getPhone());
          oldUser.setEnabled(newUser.getEnabled());
          oldUser.setProfile(newUser.getProfile());
          User temp=userRepository.findByUsername(newUser.getUsername());
          if(temp!=null){
            throw new DuplicateResourceException("user","username",newUser.getUsername());
          }
          oldUser.setUsername(newUser.getUsername());
          newUser=this.userRepository.save(oldUser);
      }
      return this.usertoDto(oldUser);
    }

    
}
