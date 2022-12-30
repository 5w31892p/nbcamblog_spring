package com.sparta.nbcamblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.nbcamblog.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
