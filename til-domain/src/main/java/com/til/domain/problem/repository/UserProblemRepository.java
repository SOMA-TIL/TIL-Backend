package com.til.domain.problem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.til.domain.problem.model.UserProblem;

public interface UserProblemRepository extends JpaRepository<UserProblem, Long> {
}
