package com.ayushsingh.assessmentportal.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequest {
    private String to;
    private String subject;
    private String message;

    public EmailRequest() {
    }

    public EmailRequest(String from, String subject, String message) {
        this.to = from;
        this.subject = subject;
        this.message = message;
    }

    @Override
    public String toString() {
        return "EmailRequest [from=" + to + ", subject=" + subject + ", message=" + message + "]";
    }

    

}
