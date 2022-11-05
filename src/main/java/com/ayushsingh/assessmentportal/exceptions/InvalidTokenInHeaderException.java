package com.ayushsingh.assessmentportal.exceptions;



public class InvalidTokenInHeaderException extends RuntimeException{
  
    String message;
    public InvalidTokenInHeaderException(String t,String m){
        message=m+" "+t;
    }
    
}
