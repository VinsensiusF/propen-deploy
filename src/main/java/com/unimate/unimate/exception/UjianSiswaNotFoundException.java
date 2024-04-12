package com.unimate.unimate.exception;

import org.springframework.http.HttpStatus;

public class UjianSiswaNotFoundException extends BaseServiceException {
    public static final String DEFAULT_MESSAGE = "UjianSiswa not found.";
    public static final String DEFAULT_TITLE = UjianSiswaNotFoundException.class.getSimpleName();
    public static final int DEFAULT_HTTP_CODE = HttpStatus.BAD_REQUEST.value();

    public UjianSiswaNotFoundException() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_HTTP_CODE);
    };
}
