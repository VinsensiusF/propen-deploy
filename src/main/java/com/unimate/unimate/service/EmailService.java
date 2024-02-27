package com.unimate.unimate.service;

import java.util.HashMap;

public interface EmailService {
    void send(String email, HashMap<String, String> body);

}