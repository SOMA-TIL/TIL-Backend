package com.til.domain.problem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.til.domain.problem.model.FavoriteProblem;

public interface FavoriteProblemRepository extends JpaRepository<FavoriteProblem, Long> {

    boolean existsByUserIdAndProblemId(Long userId, Long problemId);

    void deleteByUserIdAndProblemId(Long userId, Long problemId);
}
