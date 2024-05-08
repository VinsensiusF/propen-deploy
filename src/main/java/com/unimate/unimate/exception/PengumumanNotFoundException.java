package com.unimate.unimate.exception;

import org.springframework.http.HttpStatus;

public class PengumumanNotFoundException extends BaseServiceException {
    public static final String DEFAULT_MESSAGE = "Pengumuman not found!";
    public static final String DEFAULT_TITLE = PengumumanNotFoundException.class.getSimpleName();
    public static final int DEFAULT_HTTP_CODE = HttpStatus.NOT_FOUND.value();

    public PengumumanNotFoundException() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_HTTP_CODE);
    }
}
