package com.ayushsingh.assessmentportal.model;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

//To be used by spring security
@NoArgsConstructor
@AllArgsConstructor
public class Authority implements GrantedAuthority{

    private String authority;
    /*
     * Whenever string uses authority, this
     * method will be called to get the authority
     */
    @Override
    public String getAuthority() {
        return this.authority;
    }
    
}
