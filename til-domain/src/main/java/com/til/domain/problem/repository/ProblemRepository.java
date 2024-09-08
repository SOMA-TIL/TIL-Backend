package com.til.domain.problem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.til.common.exception.BaseException;
import com.til.domain.problem.enums.ProblemErrorCode;
import com.til.domain.problem.model.Problem;

public interface ProblemRepository extends JpaRepository<Problem, Long>, ProblemRepositoryCustom,
    AdminProblemRepositoryCustom {

    default Problem getById(Long id) {
        return findById(id).orElseThrow(() -> new BaseException(ProblemErrorCode.NOT_FOUND_PROBLEM));
    }
}
