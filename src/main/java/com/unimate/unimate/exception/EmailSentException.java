package com.unimate.unimate.exception;

import org.springframework.http.HttpStatus;

public class EmailSentException extends BaseServiceException{
    public static final String DEFAULT_MESSAGE = "Email has been sent before. Please wait for 15 minutes";
    public static final String DEFAULT_TITLE = EmailSentException.class.getSimpleName();
    public static final int DEFAULT_HTTP_CODE = HttpStatus.BAD_REQUEST.value();

    public EmailSentException() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_HTTP_CODE);
    };
}