package com.sparta.nbcamblog.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatusResponse {
    private int statusCode;
    private String message;

    @Builder
    public StatusResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public StatusResponse(StatusEnum code) {
        this.statusCode = code.getStatusCode();
        this.message = code.getMessage();
    }
}
