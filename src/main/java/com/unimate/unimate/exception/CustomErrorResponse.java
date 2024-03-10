package com.unimate.unimate.exception;

import lombok.Data;

import java.time.Instant;
import java.time.temporal.ChronoField;

@Data
public class CustomErrorResponse {
    private String timestamp;
    private int statusCode;
    private String title;
    private String message;


    public CustomErrorResponse(int statusCode, String title, String message) {
        this.timestamp = Instant.now().with(ChronoField.NANO_OF_SECOND, 0).toString();
        this.statusCode = statusCode;
        this.title = title;
        this.message = message;
    }
}
