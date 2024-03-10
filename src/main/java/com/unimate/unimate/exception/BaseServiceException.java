package com.unimate.unimate.exception;

import lombok.Data;

@Data
public class BaseServiceException extends RuntimeException {
    private final String title;
    private final int httpStatusCode;
    private final Object[] args;

    public BaseServiceException(String title, String message, int httpStatusCode){
        super(message);
        this.title = title;
        this.httpStatusCode = httpStatusCode;
        this.args = new Object[]{};
    }
    public BaseServiceException(String title, String message, int httpStatusCode, Object[] args) {
        super(message);
        this.title = title;
        this.httpStatusCode = httpStatusCode;
        this.args = args;
    }
    public CustomErrorResponse generateCustomErrorResponse() {
        return new CustomErrorResponse(httpStatusCode, title, getMessage());
    }
}
