package com.sparta.nbcamblog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.nbcamblog.entity.Comment;
import com.sparta.nbcamblog.entity.NestedReply;

public interface NestedReplyRepository extends JpaRepository<NestedReply , Long> {
	List<NestedReply> findAllByOrderByModifiedAtDesc();
	Optional<NestedReply> findByIdAndUserId(Long id, Long userId);
}
