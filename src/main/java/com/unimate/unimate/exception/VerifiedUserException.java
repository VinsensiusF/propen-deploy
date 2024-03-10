package com.unimate.unimate.exception;

import org.springframework.http.HttpStatus;

public class VerifiedUserException extends BaseServiceException{
    public static final String DEFAULT_MESSAGE = "User has already been verified.";
    public static final String DEFAULT_TITLE = VerifiedUserException.class.getSimpleName();
    public static final int DEFAULT_HTTP_CODE = HttpStatus.BAD_REQUEST.value();

    public VerifiedUserException() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_HTTP_CODE);
    };
}