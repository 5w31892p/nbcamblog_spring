package com.sparta.nbcamblog.exception;

import lombok.Getter;

@Getter
public class CustomStatus extends RuntimeException {
    private StatusEnum error;

    public CustomStatus(StatusEnum e) {
        super(e.getMessage());
        this.error = e;
    }
}
