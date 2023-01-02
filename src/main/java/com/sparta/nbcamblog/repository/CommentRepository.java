package com.sparta.nbcamblog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.nbcamblog.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByOrderByModifiedAtDesc();
    Optional<Comment> findByIdAndUserId(Long id, Long userId);

}
