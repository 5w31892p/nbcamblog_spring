package com.sparta.nbcamblog.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExceptionEnum {

    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, 400),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, 401),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, 400, "토큰이 유효하지 않습니다."),
    UNAUTHENTICATED_TOKEN(HttpStatus.BAD_REQUEST, 400, "작성자 또는 관리자만 삭제 및 수정할 수 있습니다."),
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, 400, "중복된 사용자가 존재합니다."),
    UNINFORMED_USERNAME(HttpStatus.BAD_REQUEST, 400, "등록되지 않은 사용자입니다."),
    UNINFORMED_PASSWORD(HttpStatus.BAD_REQUEST, 400, "비밀번호가 일치하지 않습니다."),
    UNAUTHORIZED_ADMIN(HttpStatus.UNAUTHORIZED, 401, "관리자 암호가 틀려 등록이 불가능합니다."),
    NO_POST(HttpStatus.NOT_FOUND, 404, "게시글이 존재하지 않습니다."),
    NO_COMMENT(HttpStatus.NOT_FOUND, 404, "게시글이 존재하지 않습니다.");
    private final HttpStatus status;
    private final int statusCode;
    private String message;

    ExceptionEnum(HttpStatus status, int statusCode) {
        this.status = status;
        this.statusCode = statusCode;
    }
    ExceptionEnum(HttpStatus status, int statusCode, String message) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
    }
}
