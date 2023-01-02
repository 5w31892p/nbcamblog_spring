package com.sparta.nbcamblog.dto;

import com.sparta.nbcamblog.entity.UserRoleEnum;

import lombok.Getter;

@Getter
public class AuthenticatedUser {
    private final UserRoleEnum userRoleEnum;
    private final String username;

    public AuthenticatedUser(UserRoleEnum userRoleEnum, String username) {
        this.userRoleEnum = userRoleEnum;
        this.username = username;
    }
}
