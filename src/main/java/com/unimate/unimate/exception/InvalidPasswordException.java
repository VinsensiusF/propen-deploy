package com.unimate.unimate.exception;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends BaseServiceException{
    public static final String DEFAULT_MESSAGE = "Wronf password!";
    public static final String DEFAULT_TITLE = InvalidPasswordException.class.getSimpleName();
    public static final int DEFAULT_HTTP_CODE = HttpStatus.BAD_REQUEST.value();

    public InvalidPasswordException() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_HTTP_CODE);
    };
}
