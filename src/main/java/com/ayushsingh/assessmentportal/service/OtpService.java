package com.ayushsingh.assessmentportal.service;

public interface OtpService {
    public int generateOTP(String key);
    public int getOTP(String key);
    public void clearOTP(String key);
}
