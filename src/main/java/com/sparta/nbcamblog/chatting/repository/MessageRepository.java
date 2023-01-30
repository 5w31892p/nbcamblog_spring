package com.sparta.nbcamblog.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.nbcamblog.chatting.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
