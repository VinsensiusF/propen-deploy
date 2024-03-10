package com.unimate.unimate.service;

import com.unimate.unimate.entity.EmailDetails;

import java.util.HashMap;

public interface EmailService {
    String send(String email, HashMap<String, String> body);

    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);

}