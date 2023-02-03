package com.sparta.nbcamblog.chatting.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@IdClass(MessageRoomId.class)
public class Message {

	@Column(nullable = false)
	private String sender;

	@Column(nullable = false)
	private String receiver;

	@Column(columnDefinition = "TEXT")
	private String message;
	@Id
	@Column
	private long blogId;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime sendDate;

	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "room_id")
	private ChatRooms room;

	public Message(String message, ChatRooms room) {
		this.sender = room.getUser().getUsername();
		this.receiver = room.getUser().getUsername();
		this.message = message;
		this.blogId = room.getBlogId();
		this.sendDate = LocalDateTime.now();
		this.room = room;
	}
}
