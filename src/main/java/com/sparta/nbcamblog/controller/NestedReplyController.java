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
import com.sparta.nbcamblog.dto.NestedReplyRequestDto;
import com.sparta.nbcamblog.dto.NestedReplyResponseDto;
import com.sparta.nbcamblog.exception.StatusResponse;
import com.sparta.nbcamblog.jwt.JwtUtil;
import com.sparta.nbcamblog.service.JwtService;
import com.sparta.nbcamblog.service.NestedReplyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NestedReplyController {

	private final NestedReplyService replyService;
	private final JwtUtil jwtUtil;
	private final JwtService jwtService;

	@PostMapping("comment/{commentId}/reply")
	public NestedReplyResponseDto createReply(@PathVariable Long commentId, @RequestBody NestedReplyRequestDto requestDto, HttpServletRequest request) {
		String token = jwtUtil.resolveToken(request);
		AuthenticatedUser authenticatedUser = jwtService.validateAndGetInfo(token);
		return replyService.createReply(commentId, requestDto, authenticatedUser.getUsername());
	}

	@GetMapping("comments/{commentId}/replys")
	public List<NestedReplyResponseDto> getReply(@PathVariable Long commentId) {
		return replyService.getReply(commentId);
	}

	@PutMapping("comments/{commentId}/reply/{id}")
	public NestedReplyResponseDto updateReply (@PathVariable Long id, @PathVariable Long commentId, @RequestBody NestedReplyRequestDto requestDto, HttpServletRequest request) {
		String token = jwtUtil.resolveToken(request);
		AuthenticatedUser authenticatedUser = jwtService.validateAndGetInfo(token);
		return replyService.updateReply(commentId, id, requestDto, authenticatedUser.getUsername());
	}

	@DeleteMapping("comments/{commentId}/reply/{id}")
	public StatusResponse deleteReply (@PathVariable Long id, @PathVariable Long commentId, HttpServletRequest request) {
		String token = jwtUtil.resolveToken(request);
		AuthenticatedUser authenticatedUser = jwtService.validateAndGetInfo(token);
		return replyService.deleteReply(commentId, id,authenticatedUser.getUsername());
	}
}
