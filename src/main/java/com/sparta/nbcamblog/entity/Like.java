package com.sparta.nbcamblog.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;

@Getter
@Entity
public class Like {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	private Long postId;
	private Long CommentId;

	public Like(BlogUser user, Blog post, Comment comment) {
		this.userId = user.getId();
		this.postId = post.getId();
		CommentId = comment.getId();
	}
}
