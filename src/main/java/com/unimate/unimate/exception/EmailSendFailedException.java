package com.unimate.unimate.exception;

import org.springframework.http.HttpStatus;

public class EmailSendFailedException extends BaseServiceException{
    public static final String DEFAULT_MESSAGE = "Failed to send email.";
    public static final String DEFAULT_TITLE = EmailSentException.class.getSimpleName();
    public static final int DEFAULT_HTTP_CODE = HttpStatus.BAD_REQUEST.value();

    public EmailSendFailedException() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_HTTP_CODE);
    };
}