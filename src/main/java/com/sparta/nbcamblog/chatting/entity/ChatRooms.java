package com.sparta.nbcamblog.chatting.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.sparta.nbcamblog.entity.Blog;
import com.sparta.nbcamblog.entity.BlogUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRooms {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private long id;

	@Column(nullable = false, name = "blog_id")
	private long blogId;

	@Column(nullable = false, name = "writer")
	private String writer;

	@Column(nullable = false, name = "visitor")
	private String visitor;

	@OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
	private List<Message> message = new ArrayList<>();

	public ChatRooms(Blog blog, BlogUser user) {
		this.blogId = blog.getId();
		this.writer = blog.getUser().getUsername();
		this.visitor = user.getUsername();
	}

	// public boolean checkAuthorization(User user) {
	// 	return Objects.equals(id, user.getId());
	// }
}
