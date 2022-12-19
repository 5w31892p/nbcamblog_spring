package com.sparta.nbcamblog.repository;

import com.sparta.nbcamblog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByOrderByModifiedAtDesc();
    Optional<Comment> findByIdAndUserId(Long id, Long userId);

}
