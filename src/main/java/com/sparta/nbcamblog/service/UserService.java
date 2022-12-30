package com.sparta.nbcamblog.service;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.nbcamblog.dto.LoginRequestDto;
import com.sparta.nbcamblog.dto.SignupRequestDto;
import com.sparta.nbcamblog.entity.BlogUser;
import com.sparta.nbcamblog.entity.UserRoleEnum;
import com.sparta.nbcamblog.exception.CustomStatus;
import com.sparta.nbcamblog.exception.StatusEnum;
import com.sparta.nbcamblog.jwt.JwtUtil;
import com.sparta.nbcamblog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        Optional<BlogUser> found = userRepository.findByUsername(signupRequestDto.getUsername());
        if (found.isPresent()) {
            throw new CustomStatus(StatusEnum.DUPLICATE_USERNAME);
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new CustomStatus(StatusEnum.UNAUTHORIZED_ADMIN);
            }
            role = UserRoleEnum.ADMIN;
        }
        BlogUser user = new BlogUser(signupRequestDto.getUsername(), signupRequestDto.getPassword(), role);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        // 사용자 확인
        BlogUser user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(
                () -> new CustomStatus(StatusEnum.UNINFORMED_USERNAME)
        );
        // 비밀번호 확인
        if(!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())){
            throw new CustomStatus(StatusEnum.UNINFORMED_PASSWORD);
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
    }
}
