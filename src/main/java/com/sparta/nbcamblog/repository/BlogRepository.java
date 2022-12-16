package com.sparta.nbcamblog.repository;

import com.sparta.nbcamblog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByOrderByModifiedAtDesc();

    List<Blog> getPostingByUsername(String username);
    Blog getById(Long id);
}
