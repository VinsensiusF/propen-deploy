package com.unimate.unimate.exception.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Customized error response for handling Error Response when exception thrown
 */
@Data
@Accessors(chain = true)
public class ErrorResponseDTO {
    private int code;
    private String message;
}
