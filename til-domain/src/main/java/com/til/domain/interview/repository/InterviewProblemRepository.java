package com.til.domain.interview.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.til.domain.interview.model.InterviewProblem;

public interface InterviewProblemRepository extends JpaRepository<InterviewProblem, Long>,
    InterviewProblemRepositoryCustom {

}
