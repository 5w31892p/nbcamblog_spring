package com.sparta.nbcamblog.chatting.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
	@GeneratedValue
	@Column(name = "room_id")
	private long id;

	@Column(nullable = false, name = "product_id")
	private long blogId;

	@Column(nullable = false, name = "seller_id")
	private long writerId;

	@Column(nullable = false, name = "customer_id")
	private long visitorId;

	@OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
	private List<Message> message = new ArrayList<>();

	public ChatRooms(Blog blog, BlogUser user) {
		this.blogId = blog.getId();
		this.writerId = blog.getUser().getId();
		this.visitorId = user.getId();
	}

	// public boolean checkAuthorization(User user) {
	// 	return Objects.equals(id, user.getId());
	// }
}
