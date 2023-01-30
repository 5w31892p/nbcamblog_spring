package com.sparta.nbcamblog.chatting.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.nbcamblog.chatting.entity.ChatRooms;
import com.sparta.nbcamblog.chatting.repository.RoomRepository;
import com.sparta.nbcamblog.entity.Blog;
import com.sparta.nbcamblog.entity.BlogUser;
import com.sparta.nbcamblog.repository.BlogRepository;
import com.sparta.nbcamblog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {
	private final RoomRepository roomRepository;
	private final BlogRepository blogRepository;
	private final UserRepository userRepository;

	/**
	 * 채팅방 만들기
	 *
	 */
	public void createRoom(long blogId, String username) {
		Blog blog = blogRepository.findById(blogId).orElseThrow(
			() -> new IllegalArgumentException("상품이 존재하지 않습니다.")
		);

		BlogUser user = userRepository.findByUsername(username).orElseThrow(
			() -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
		);
			ChatRooms chatRooms = new ChatRooms(blog, user);
			roomRepository.save(chatRooms);
	}
}
