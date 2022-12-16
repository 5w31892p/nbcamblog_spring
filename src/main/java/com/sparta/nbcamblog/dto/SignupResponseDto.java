package com.sparta.nbcamblog.dto;

import lombok.Getter;

@Getter
public class SignupResponseDto {
    private String msg = "회원가입에 성공하였습니다.";
    private int statuscode = 200;
}
