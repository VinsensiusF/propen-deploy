package com.unimate.unimate.exception;

import org.springframework.http.HttpStatus;

public class UjianNotFoundException extends BaseServiceException{
    public static final String DEFAULT_MESSAGE = "Ujian is not found.";
    public static final String DEFAULT_TITLE = UjianNotFoundException.class.getSimpleName();
    public static final int DEFAULT_HTTP_CODE = HttpStatus.NOT_FOUND.value();

    public UjianNotFoundException() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_HTTP_CODE);
    };
}