package com.sparta.nbcamblog.repository;

import com.sparta.nbcamblog.entity.Comment;
import com.sparta.nbcamblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByUser(User user);
}
