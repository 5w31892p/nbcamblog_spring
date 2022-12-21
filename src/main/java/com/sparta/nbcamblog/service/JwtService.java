package com.sparta.nbcamblog.service;

import com.sparta.nbcamblog.dto.AuthenticatedUser;
import com.sparta.nbcamblog.entity.UserRoleEnum;
import com.sparta.nbcamblog.exception.CustomStatus;
import com.sparta.nbcamblog.exception.StatusEnum;
import com.sparta.nbcamblog.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final JwtUtil jwtUtil;

    public JwtService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    public AuthenticatedUser validateAndGetInfo(String token) {
            if (jwtUtil.validateToken(token)) {
                Claims claims = jwtUtil.getUserInfoFromToken(token);
                String username = claims.getSubject();
                UserRoleEnum role = UserRoleEnum.valueOf(claims.get("auth").toString()); // String으로 enum을 만들어줌
                return new AuthenticatedUser(role, username);
            } else {
                throw new CustomStatus(StatusEnum.INVALID_TOKEN);
//                throw new IllegalArgumentException("Token Error");
            }
        }
    }