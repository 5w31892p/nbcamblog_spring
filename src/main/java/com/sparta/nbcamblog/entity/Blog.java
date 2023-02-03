package com.sparta.nbcamblog.entity;

import java.util.ArrayList;
import java.util.List;

import com.sparta.nbcamblog.dto.BlogRequestDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Blog extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false, length = 200)
	private String title;

	@Column(nullable = false)
	private String content;

	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "id")
	private BlogUser user;

	@OneToMany(mappedBy = "blog", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER) // 게시물 삭제 시 해당 코멘트 모두 삭제
	private List<Comment> commentList = new ArrayList<>();


	@ManyToMany
	private List<BlogUser> likes = new ArrayList<>();

	public Blog(BlogRequestDto blogRequestDto, BlogUser user) {
		this.username = user.getUsername();
		this.title = blogRequestDto.getTitle();
		this.content = blogRequestDto.getContent();
		this.user = user;
	}

	public void update(BlogRequestDto blogRequestDto) {
		this.title = blogRequestDto.getTitle();
		this.content = blogRequestDto.getContent();
	}
}
