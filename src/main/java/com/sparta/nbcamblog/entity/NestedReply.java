package com.sparta.nbcamblog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.sparta.nbcamblog.dto.NestedReplyRequestDto;

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
