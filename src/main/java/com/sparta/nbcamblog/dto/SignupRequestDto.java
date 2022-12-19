package com.sparta.nbcamblog.dto;


import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
public class SignupRequestDto {
    @Pattern(regexp="^.*(?=^.{4,10}$)(?=.*\\d)(?=.*[a-z]).*$", message = "아이디는 영어(소문자)와 숫자를 포함해서 4~10자리 이내로 입력해주세요.")
    private String username;
    @Pattern(regexp="^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message = "비밀번호는 영어(대소문자)와 숫자, 특수문자를 포함해서 8~15자리 이내로 입력해주세요.")
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}
