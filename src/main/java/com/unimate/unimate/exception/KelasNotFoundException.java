package com.unimate.unimate.exception;

import org.springframework.http.HttpStatus;

public class KelasNotFoundException extends BaseServiceException {
    public static final String DEFAULT_MESSAGE = "Kelas not found.";
    public static final String DEFAULT_TITLE = KelasNotFoundException.class.getSimpleName();
    public static final int DEFAULT_HTTP_CODE = HttpStatus.BAD_REQUEST.value();

    public KelasNotFoundException() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_HTTP_CODE);
    };
}
