package com.sparta.nbcamblog.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private ExceptionEnum error;

    public CustomException(ExceptionEnum e) {
        super(e.getMessage());
        this.error = e;
    }
}
