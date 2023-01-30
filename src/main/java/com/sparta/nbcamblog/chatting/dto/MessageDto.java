package com.sparta.nbcamblog.chatting.dto;

import com.sparta.nbcamblog.chatting.entity.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
	private long roomId;
	private long blogId;
	private String sender;
	private String receiver;
	private String message;

	public MessageDto(Message message) {
		this.roomId = message.getRoom().getId();
		this.blogId = message.getBlogId();
		this.sender = message.getSender();
		this.receiver = message.getReceiver();
		this.message = message.getMessage();
	}
}
