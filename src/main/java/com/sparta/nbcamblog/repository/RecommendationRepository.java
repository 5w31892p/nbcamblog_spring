package com.sparta.nbcamblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.nbcamblog.entity.Recommendation;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
}
