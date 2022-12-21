package com.sparta.nbcamblog.controller;

import com.sparta.nbcamblog.dto.LoginRequestDto;
import com.sparta.nbcamblog.dto.SignupRequestDto;
import com.sparta.nbcamblog.exception.StatusEnum;
import com.sparta.nbcamblog.exception.StatusResponse;
import com.sparta.nbcamblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public StatusResponse signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return new StatusResponse(StatusEnum.SIGNUP);
    }

    @ResponseBody
    @PostMapping("/login")
    public StatusResponse login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return new StatusResponse(StatusEnum.LOGIN);
    }
}
