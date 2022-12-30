package com.sparta.nbcamblog.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.nbcamblog.exception.CustomStatus;
import com.sparta.nbcamblog.exception.StatusEnum;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
	public final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String token = jwtUtil.resolveToken(request);

		if (token!=null) {
			if (!jwtUtil.validateToken(token)) {
				jwtExceptionHandler(response, StatusEnum.INVALID_TOKEN);
				return;
			}
			Claims info = jwtUtil.getUserInfoFromToken(token);
			setAuthentication(info.getSubject());
		}
		filterChain.doFilter(request, response);
	}

	public void setAuthentication(String username) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		Authentication authentication = jwtUtil.createAuthentication(username);
		context.setAuthentication(authentication);

		SecurityContextHolder.setContext(context);
	}
	public void jwtExceptionHandler(HttpServletResponse response, StatusEnum statusEnum) {
		// 토큰 오류시 클라이언트에게 Exception 처리 값을 알려주는 함수이다.
		response.setStatus(statusEnum.getStatusCode());
		response.setContentType("application/json");
		try {
			String json = new ObjectMapper().writeValueAsString(new CustomStatus(StatusEnum.INVALID_TOKEN));
			response.getWriter().write(json);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
