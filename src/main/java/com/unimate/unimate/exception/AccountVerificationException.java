package com.unimate.unimate.exception;

import org.springframework.http.HttpStatus;

public class AccountVerificationException extends BaseServiceException{
    public static final String DEFAULT_MESSAGE = "Account has on-going verification. Please check youe email or you can try again in 15 minutes.";
    public static final String DEFAULT_TITLE = AccountVerificationException.class.getSimpleName();
    public static final int DEFAULT_HTTP_CODE = HttpStatus.BAD_REQUEST.value();

    public AccountVerificationException() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_HTTP_CODE);
    };
}