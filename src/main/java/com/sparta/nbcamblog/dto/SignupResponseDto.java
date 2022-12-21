package com.sparta.nbcamblog.dto;

import lombok.Getter;

@Getter
public class SignupResponseDto {
    private int statusCode = 200;
    private String message = "회원가입에 성공하였습니다.";
}
