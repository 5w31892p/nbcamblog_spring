package com.sparta.nbcamblog.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.sparta.nbcamblog.exception.CustomStatus;
import com.sparta.nbcamblog.exception.StatusEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {
    @NotEmpty(message = "username은 필수항목입니다.")
    private String username;

    @NotEmpty(message = "password는 필수항목입니다.")
    private String password;

    private boolean admin = false;

    private String adminToken = "";

}
