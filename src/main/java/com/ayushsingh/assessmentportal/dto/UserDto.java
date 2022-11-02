package com.ayushsingh.assessmentportal.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.UniqueElements;

import lombok.Data;
@Data
public class UserDto {
    
    private Long userId;
    @NotBlank(message = "Username cannot be empty")
    @Size(min=5,message="username must be greater than 4 characters")
    private String username;
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
}
