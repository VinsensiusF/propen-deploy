package com.unimate.unimate.exception;

import org.springframework.http.HttpStatus;

public class KelasSiswaNotFoundException extends BaseServiceException {
    public static final String DEFAULT_MESSAGE = "KelasSiswa not found.";
    public static final String DEFAULT_TITLE = KelasSiswaNotFoundException.class.getSimpleName();
    public static final int DEFAULT_HTTP_CODE = HttpStatus.BAD_REQUEST.value();

    public KelasSiswaNotFoundException() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_HTTP_CODE);
    };
}
