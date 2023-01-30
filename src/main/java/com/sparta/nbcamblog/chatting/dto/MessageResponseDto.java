package com.sparta.nbcamblog.chatting.dto;

import java.time.LocalDateTime;

import com.sparta.nbcamblog.chatting.entity.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseDto {
	private long roomId;
	private long blogId;
	private String sender;
	private String message;
	private LocalDateTime sendTime;

	public MessageResponseDto(Message message) {
		this.roomId = message.getRoom().getId();
		this.blogId = message.getBlogId();
		this.sender = message.getSender();
		this.message = message.getMessage();
		this.sendTime = message.getSendDate();
	}
}
