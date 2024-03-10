package com.unimate.unimate.exception;

import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends BaseServiceException{
    public static final String DEFAULT_MESSAGE = "Account not found.";
    public static final String DEFAULT_TITLE = AccountNotFoundException.class.getSimpleName();
    public static final int DEFAULT_HTTP_CODE = HttpStatus.BAD_REQUEST.value();

    public AccountNotFoundException() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_HTTP_CODE);
    };
}