package com.til.domain.interview.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.til.domain.interview.model.ExperienceInterviewProblem;

public interface ExperienceInterviewProblemRepository extends JpaRepository<ExperienceInterviewProblem, Long>,
    ExperienceInterviewProblemRepositoryCustom {

}
