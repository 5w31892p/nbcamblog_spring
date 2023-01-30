package com.sparta.nbcamblog.chatting.service;

import org.springframework.stereotype.Service;

import com.sparta.nbcamblog.chatting.dto.MessageDto;
import com.sparta.nbcamblog.chatting.entity.ChatRooms;
import com.sparta.nbcamblog.chatting.entity.Message;
import com.sparta.nbcamblog.chatting.repository.MessageRepository;
import com.sparta.nbcamblog.chatting.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {
	private final MessageRepository messageRepository;
	private final RoomRepository roomRepository;

	/**
	 * 채팅 생성
	 */
	public Message createChat(MessageDto message) {

		ChatRooms rooms = roomRepository.findById(message.getRoomId()).orElseThrow(
			() -> new IllegalArgumentException("채팅방이 존재하지 않습니다.")
		);

		// Profile profile = profileRepository.findById().orElseThrow(
		// 	() -> new IllegalArgumentException("보내는 사용자가 올바르지 않습니다.")
		// );
		// if (rooms.getSellerId() == profile.getId()) {
		// 	messageRepository.save(profile.getNickname())
		// }

		if (message.getBlogId() != rooms.getBlogId()) {
			throw new IllegalArgumentException("해당 상품의 채팅방이 존재하지 않습니다.");
		} else {
			Message messages = new Message(message.getSender(), message.getReceiver(), message.getMessage(), rooms);
			messageRepository.save(messages);

			return messages;
		}
	}
}