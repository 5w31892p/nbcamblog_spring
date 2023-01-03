package com.sparta.nbcamblog.dto;

import java.time.LocalDateTime;

import com.sparta.nbcamblog.entity.NestedReply;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NestedReplyResponseDto {
	private Long commentId;
	private Long id;
	private String username;
	private String reply;
	private LocalDateTime createAt;
	private LocalDateTime modifiedat;

	public NestedReplyResponseDto(NestedReply reply) {
		this.commentId = reply.getComment().getId();
		this.id = reply.getId();
		this.username = reply.getUser().getUsername();
		this.reply = reply.getReply();
		this.createAt = reply.getComment().getCreateAt();
		this.modifiedat = reply.getComment().getModifiedAt();
	}
}
