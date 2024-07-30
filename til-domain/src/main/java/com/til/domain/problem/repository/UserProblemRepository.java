package com.til.domain.problem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.til.domain.common.exception.BaseException;
import com.til.domain.problem.enums.ProblemErrorCode;
import com.til.domain.problem.model.UserProblem;

public interface UserProblemRepository extends JpaRepository<UserProblem, Long> {

    default UserProblem saveUserProblem(UserProblem userProblem) {
        try {
            return save(userProblem);
        } catch (Exception e) {
            throw new BaseException(ProblemErrorCode.FAILED_TO_SAVE_PROBLEM);
        }
    }
}
