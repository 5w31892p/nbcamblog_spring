package com.sparta.nbcamblog.entity;

import com.sparta.nbcamblog.dto.NestedReplyRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class NestedReply extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String reply;

	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private BlogUser user;

	@ManyToOne
	@JoinColumn(name = "COMMENT_ID", nullable = false)
	private Comment comment;

	public NestedReply(NestedReplyRequestDto requestDto, BlogUser user, Comment comment) {
		this.reply = requestDto.getReply();
		this.user = user;
		this.comment = comment;
	}

	public void update(NestedReplyRequestDto requestDto) {
		this.reply = requestDto.getReply();
	}
}
