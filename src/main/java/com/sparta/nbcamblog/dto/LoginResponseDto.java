package com.sparta.nbcamblog.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String msg = "로그인이 완료되었습니다.";
    private int statuscode = 200;
}
