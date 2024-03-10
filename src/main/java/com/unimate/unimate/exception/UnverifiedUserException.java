package com.unimate.unimate.exception;

import org.springframework.http.HttpStatus;

public class UnverifiedUserException extends BaseServiceException{
    public static final String DEFAULT_MESSAGE = "User is not verified.";
    public static final String DEFAULT_TITLE = UnverifiedUserException.class.getSimpleName();
    public static final int DEFAULT_HTTP_CODE = HttpStatus.BAD_REQUEST.value();

    public UnverifiedUserException() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_HTTP_CODE);
    };
}