package com.unimate.unimate.exception;

import org.springframework.http.HttpStatus;

public class KelasGuruNotFoundException extends BaseServiceException {

    public static final String DEFAULT_MESSAGE = "KelasGuru not found.";
    public static final String DEFAULT_TITLE = KelasGuruNotFoundException.class.getSimpleName();
    public static final int DEFAULT_HTTP_CODE = HttpStatus.NOT_FOUND.value();

    public KelasGuruNotFoundException() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_HTTP_CODE);
    };
}
