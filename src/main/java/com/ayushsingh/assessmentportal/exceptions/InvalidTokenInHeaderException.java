package com.ayushsingh.assessmentportal.exceptions;



public class InvalidTokenInHeaderException extends RuntimeException{
  
    String message;
    public InvalidTokenInHeaderException(String m){
        message=m;
    }
    @Override
    public String getMessage() {
        return message;
    }    
}
