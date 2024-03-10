package com.unimate.unimate.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends BaseServiceException{
    public static final String DEFAULT_MESSAGE = "Token is expired or invalid!";
    public static final String DEFAULT_TITLE = InvalidTokenException.class.getSimpleName();
    public static final int DEFAULT_HTTP_CODE = HttpStatus.BAD_REQUEST.value();

    public InvalidTokenException() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_HTTP_CODE);
    };
}
