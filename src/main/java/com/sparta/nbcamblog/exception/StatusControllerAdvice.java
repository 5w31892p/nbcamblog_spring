package com.sparta.nbcamblog.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class StatusControllerAdvice {
    @ExceptionHandler({CustomStatus.class})
    public ResponseEntity<StatusResponse> exceptionHandler(HttpServletRequest request, final CustomStatus e) {
        //e.printStackTrace();
        return ResponseEntity
                .status(e.getError().getStatus())
                .body(StatusResponse.builder()
                        .statusCode(e.getError().getStatusCode())
                        .message(e.getError().getMessage())
                        .build());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<StatusResponse> exceptionHandler(HttpServletRequest request, final RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(StatusEnum.RUNTIME_EXCEPTION.getStatus())
                .body(StatusResponse.builder()
                        .statusCode(StatusEnum.RUNTIME_EXCEPTION.getStatusCode())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<StatusResponse> exceptionHandler(HttpServletRequest request, final AccessDeniedException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(StatusEnum.ACCESS_DENIED_EXCEPTION.getStatus())
                .body(StatusResponse.builder()
                        .statusCode(StatusEnum.ACCESS_DENIED_EXCEPTION.getStatusCode())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<StatusResponse> exceptionHandler(HttpServletRequest request, final Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(StatusEnum.INTERNAL_SERVER_ERROR.getStatus())
                .body(StatusResponse.builder()
                        .statusCode(StatusEnum.INTERNAL_SERVER_ERROR.getStatusCode())
                        .message(e.getMessage())
                        .build());
    }
}
