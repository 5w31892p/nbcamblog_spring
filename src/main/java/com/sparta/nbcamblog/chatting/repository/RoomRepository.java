package com.sparta.nbcamblog.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.nbcamblog.chatting.entity.ChatRooms;

public interface RoomRepository extends JpaRepository<ChatRooms,Long> {
}