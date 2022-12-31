package com.sparta.nbcamblog.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Recommendation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	private Long postId;
	private Long CommentId;

	public Recommendation(BlogUser user, Blog post, Comment comment) {
		this.userId = user.getId();
		this.postId = post.getId();
		CommentId = comment.getId();
	}
}
