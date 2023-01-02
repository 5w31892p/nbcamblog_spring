package com.sparta.nbcamblog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.nbcamblog.entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByOrderByModifiedAtDesc();

    Optional<Blog> findByIdAndUserId(Long postId, Long userId);
}