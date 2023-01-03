package com.sparta.nbcamblog.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.nbcamblog.dto.LoginRequestDto;
import com.sparta.nbcamblog.dto.SignupRequestDto;
import com.sparta.nbcamblog.exception.StatusEnum;
import com.sparta.nbcamblog.exception.StatusResponse;
import com.sparta.nbcamblog.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
	private final UserService userService;

	@PostMapping("/signup")
	public StatusResponse signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
		// Username 형식 확인
		if (!signupRequestDto.getUsername().matches("^.*(?=^.{4,10}$)(?=.*\\d)(?=.*[a-z]).*$")) {
			return new StatusResponse(StatusEnum.ID_TYPE);
		}
		// Password 형식 확인
		if (!signupRequestDto.getPassword().matches("^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$")) {
			return new StatusResponse(StatusEnum.PASSWORD_TYPE);
		}
		userService.signup(signupRequestDto);
		return new StatusResponse(StatusEnum.SIGNUP_OK);
	}

	@PostMapping("/login")
	public StatusResponse login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
		userService.login(loginRequestDto, response);
		return new StatusResponse(StatusEnum.LOGIN_OK);
	}
}
