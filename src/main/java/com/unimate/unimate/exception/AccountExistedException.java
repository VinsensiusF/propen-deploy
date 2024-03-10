package com.unimate.unimate.exception;

import org.springframework.http.HttpStatus;

public class AccountExistedException extends BaseServiceException{
    public static final String DEFAULT_MESSAGE = "Account has already existed.";
    public static final String DEFAULT_TITLE = AccountExistedException.class.getSimpleName();
    public static final int DEFAULT_HTTP_CODE = HttpStatus.BAD_REQUEST.value();

    public AccountExistedException() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_HTTP_CODE);
    };
}