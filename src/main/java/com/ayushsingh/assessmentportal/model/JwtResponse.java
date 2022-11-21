package com.ayushsingh.assessmentportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * This will be used to send the token back to the user
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtResponse {
    private String token;
    private String code;
    private String message;
    private String status;
}
