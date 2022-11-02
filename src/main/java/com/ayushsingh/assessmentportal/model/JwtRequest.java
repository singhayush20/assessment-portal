package com.ayushsingh.assessmentportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * We will get the username and password
 * from the user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {
    private String username;
    private String password;
}
