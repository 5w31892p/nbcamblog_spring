package com.sparta.nbcamblog.chatting.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.nbcamblog.chatting.service.RoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RoomController {
	private final RoomService roomService;

	/**
	 * 채팅방 등록
	 */

	@PostMapping("/chatrooms/{blogId}")
	public ResponseEntity<String> createRoom(@PathVariable Long blogId, @AuthenticationPrincipal UserDetails userDetails) {

		roomService.createRoom(blogId, userDetails.getUsername());

		return new ResponseEntity<>("채팅방 생성 완료", HttpStatus.CREATED);
	}
}