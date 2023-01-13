package com.sparta.nbcamblog.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.nbcamblog.dto.NestedReplyRequestDto;
import com.sparta.nbcamblog.dto.NestedReplyResponseDto;
import com.sparta.nbcamblog.exception.StatusResponse;
import com.sparta.nbcamblog.service.NestedReplyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NestedReplyController {

	private final NestedReplyService replyService;

	@PostMapping("comment/{commentId}/reply")
	public NestedReplyResponseDto createReply(@PathVariable Long commentId, @RequestBody NestedReplyRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails) {
		return replyService.createReply(commentId, requestDto, userDetails.getUsername());
	}

	@GetMapping("comments/{commentId}/replys")
	public List<NestedReplyResponseDto> getReply(@PathVariable Long commentId) {
		return replyService.getReply(commentId);
	}

	@PutMapping("comments/{commentId}/reply/{id}")
	public NestedReplyResponseDto updateReply (@PathVariable Long id, @PathVariable Long commentId, @RequestBody NestedReplyRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails) {
		return replyService.updateReply(commentId, id, requestDto, userDetails.getUsername());
	}

	@DeleteMapping("comments/{commentId}/reply/{id}")
	public StatusResponse deleteReply (@PathVariable Long id, @PathVariable Long commentId, @AuthenticationPrincipal UserDetails userDetails) {
		return replyService.deleteReply(commentId, id,userDetails.getUsername());
	}
}
