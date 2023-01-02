package com.sparta.nbcamblog.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.nbcamblog.dto.AuthenticatedUser;
import com.sparta.nbcamblog.dto.BlogRequestDto;
import com.sparta.nbcamblog.dto.BlogResponseDto;
import com.sparta.nbcamblog.exception.StatusEnum;
import com.sparta.nbcamblog.exception.StatusResponse;
import com.sparta.nbcamblog.jwt.JwtUtil;
import com.sparta.nbcamblog.service.BlogService;
import com.sparta.nbcamblog.service.JwtService;
import com.sparta.nbcamblog.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogController {
	private final BlogService blogService;
	private final UserService userService;
	private final JwtUtil jwtUtil;
	private final JwtService jwtService;

	@PostMapping("/post")
	public BlogResponseDto createContent(@RequestBody BlogRequestDto blogRequestDto, HttpServletRequest request) {
		String token = jwtUtil.resolveToken(request);
		AuthenticatedUser authenticatedUser = jwtService.validateAndGetInfo(token);
		return blogService.createContent(blogRequestDto, authenticatedUser.getUsername());
	}

	@GetMapping("/posts")
	public List<BlogResponseDto> getContents() {
		return blogService.getContents();
	}

	@GetMapping("/post/{id}")
	public BlogResponseDto getContent(@PathVariable Long id) {
		return blogService.getContent(id);
	}

	@PutMapping("/post/{id}")
	public BlogResponseDto updateContent(@PathVariable Long id, @RequestBody BlogRequestDto blogRequestDto,
		HttpServletRequest request) {
		String token = jwtUtil.resolveToken(request);
		AuthenticatedUser authenticatedUser = jwtService.validateAndGetInfo(token);
		return blogService.updateContent(id, blogRequestDto, authenticatedUser.getUsername());
	}

	@DeleteMapping("/post/{id}")
	public StatusResponse deletePost(@PathVariable Long id, HttpServletRequest request) {
		String token = jwtUtil.resolveToken(request);
		AuthenticatedUser authenticatedUser = jwtService.validateAndGetInfo(token);
		return blogService.deletePost(id, authenticatedUser.getUsername());
	}

	@GetMapping("/post/like/{id}")
	public StatusResponse questionVote(@PathVariable Long id, HttpServletRequest request) {
		String token = jwtUtil.resolveToken(request);
		AuthenticatedUser authenticatedUser = jwtService.validateAndGetInfo(token);
		this.blogService.addLike(id, authenticatedUser.getUsername());
		return new StatusResponse(StatusEnum.Like_OK);
	}
}
