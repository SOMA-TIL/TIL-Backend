package com.til.domain.problem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.til.domain.problem.model.Problem;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
