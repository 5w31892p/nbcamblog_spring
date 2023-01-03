package com.sparta.nbcamblog.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusEnum {
    // Success
    SIGNUP_OK(HttpStatus.OK, 200, "회원가입에 성공하였습니다."),
    LOGIN_OK(HttpStatus.OK, 200, "로그인이 완료되었습니다."),
    Like_OK(HttpStatus.OK, 200, "좋아요가 완료 되었습니다."),
    Like_Cancellation_OK(HttpStatus.OK, 200, "좋아요가 취소 되었습니다."),
    DELETE_OK(HttpStatus.OK, 200, "삭제가 완료되었습니다."),
    // exception
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, 400, "BAD_REQUEST"),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, 401, "UNAUTHORIZED"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "INTERNAL_SERVER_ERROR"),

    ID_TYPE(HttpStatus.BAD_REQUEST, 400, "username의 형식은 알파벳 소문자와 숫자를 포함한 4~10자 입니다."),
    PASSWORD_TYPE(HttpStatus.BAD_REQUEST, 400, "password의 형식은 알파벳 대소문자와 숫자, 그리고 특수문자를 포함한 8~15자 입니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, 400, "토큰이 유효하지 않습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 401, "만료된 JWT TOKEN 입니다."),
    NOT_SUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, 401, "지원되지 않는 JWT TOKEN 입니다."),
    WRONG_TOKEN(HttpStatus.UNAUTHORIZED, 401, "잘못된 JWT TOKEN 입니다."),
    UNAUTHENTICATED_TOKEN(HttpStatus.BAD_REQUEST, 400, "작성자 또는 관리자만 삭제 및 수정할 수 있습니다."),
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, 400, "중복된 사용자가 존재합니다."),
    UNINFORMED_USERNAME(HttpStatus.BAD_REQUEST, 400, "등록되지 않은 사용자입니다."),
    UNINFORMED_PASSWORD(HttpStatus.BAD_REQUEST, 400, "비밀번호가 일치하지 않습니다."),
    UNAUTHORIZED_ADMIN(HttpStatus.UNAUTHORIZED, 401, "관리자 암호가 틀려 등록이 불가능합니다."),
    NO_POST(HttpStatus.NOT_FOUND, 404, "해당 게시글이 존재하지 않습니다."),
    NO_COMMENT(HttpStatus.NOT_FOUND, 404, "해당 댓글이 존재하지 않습니다.");
    private final HttpStatus status;
    private final int statusCode;
    private String message;

    StatusEnum(HttpStatus status, int statusCode) {
        this.status = status;
        this.statusCode = statusCode;
    }

    StatusEnum(HttpStatus status, int statusCode, String message) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
    }
}
