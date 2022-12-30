package com.sparta.nbcamblog.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {
    @Pattern(regexp="^.*(?=^.{4,10}$)(?=.*\\d)(?=.*[a-z]).*$", message = "아이디는 영어(소문자)와 숫자를 포함해서 4~10자리 이내로 입력해주세요.")
    @NotEmpty(message = "username은 필수항목입니다.")
    private String username;

    @Pattern(regexp="^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message = "비밀번호는 영어(대소문자)와 숫자, 특수문자를 포함해서 8~15자리 이내로 입력해주세요.")
    @NotEmpty(message = "password는 필수항목입니다.")

    private String password;

    private boolean admin = false;

    private String adminToken = "";

}
