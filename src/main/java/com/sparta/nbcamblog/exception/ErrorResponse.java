package com.sparta.nbcamblog.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    private int statusCode;
    private String message;

    @Builder
    public ErrorResponse( int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ErrorResponse(ExceptionEnum code) {
        this.statusCode = code.getStatusCode();
        this.message = code.getMessage();
    }
}
