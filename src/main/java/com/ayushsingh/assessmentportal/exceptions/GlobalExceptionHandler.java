package com.ayushsingh.assessmentportal.exceptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.AddressException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ayushsingh.assessmentportal.constants.AppConstants;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
    @ExceptionHandler(ResourceNotFoundException.class) // add comma separated list of Exception classes
    public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException ex) {
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(AppConstants.ERROR_CODE, message, AppConstants.ERROR_MESSAGE);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DuplicateResourceException.class) // add comma separated list of Exception classes
    public ResponseEntity<ApiResponse> duplicateResourceFoundException(DuplicateResourceException ex) {
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(AppConstants.ERROR_CODE, message, AppConstants.ERROR_MESSAGE);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(AddressException.class)
    public ResponseEntity<ApiResponse> addressException(MissingServletRequestParameterException ex) {
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(AppConstants.ERROR_CODE, message, AppConstants.ERROR_MESSAGE);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    //Authentication exception
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthException(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        String fieldName = "errorMessage";
        String error = ex.getMessage();
        System.out.println("error message: "+error);
        errors.put(fieldName, error);
        errors.put("code",AppConstants.FAILURE_CODE);
        return new ResponseEntity<Map<String, String>>(errors, HttpStatus.OK);
    }
    
    @ExceptionHandler(IOException.class)
    public ResponseEntity<Map<String, String>> handleIOException(IOException ex) {
        Map<String, String> errors = new HashMap<>();
        String fieldName = "errorMessage";
        String error = ex.getMessage();
        errors.put(fieldName, error);
        return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTokenInHeaderException.class) // add comma separated list of Exception classes
    public ResponseEntity<ApiResponse> invalidTokenException(InvalidTokenInHeaderException ex) {
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(AppConstants.ERROR_CODE, message, AppConstants.ERROR_MESSAGE);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoAdminPermissionException.class)
    public ResponseEntity<ApiResponse> noPermissionException(NoAdminPermissionException ex) {
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(AppConstants.ERROR_CODE, message, AppConstants.ERROR_MESSAGE);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
