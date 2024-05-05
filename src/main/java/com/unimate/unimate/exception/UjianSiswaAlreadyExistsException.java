package com.unimate.unimate.exception;

import org.springframework.http.HttpStatus;

public class UjianSiswaAlreadyExistsException extends BaseServiceException {
    public static final String DEFAULT_MESSAGE = "UjianSiswa already exists.";
    public static final String DEFAULT_TITLE = UjianSiswaAlreadyExistsException.class.getSimpleName();
    public static final int DEFAULT_HTTP_CODE = HttpStatus.BAD_REQUEST.value();

    public UjianSiswaAlreadyExistsException() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_HTTP_CODE);
    };
}
