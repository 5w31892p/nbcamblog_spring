package com.sparta.nbcamblog.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private int statusCode = 200;
    private String message = "로그인이 완료되었습니다.";
}
