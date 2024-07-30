package com.til.application.userProblem;

import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.domain.problem.dto.UserProblemDto;
import com.til.domain.problem.dto.UserProblemResultDto;
import com.til.domain.problem.model.Problem;
import com.til.domain.problem.model.ProblemStatus;
import com.til.domain.problem.model.UserProblem;
import com.til.domain.problem.repository.ProblemRepository;
import com.til.domain.problem.repository.UserProblemRepository;
import com.til.domain.user.model.User;
import com.til.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserProblemService {

    private final UserProblemRepository userProblemRepository;

    private final ProblemRepository problemRepository;

    private final UserRepository userRepository;

    private final Random random = new Random();

    @Transactional
    public UserProblemResultDto solveProblem(UserProblemDto userProblemDto) {
        User user = userRepository.getById(userProblemDto.userId());
        Problem problem = problemRepository.getById(userProblemDto.problemId());
        UserProblem userProblem = userProblemDto.toEntity(user, problem);

        int score = calculateScore();
        ProblemStatus status = determineStatus(score);
        userProblem.setResultProblem(score, status);
        UserProblem userProblemResult = userProblemRepository.saveUserProblem(userProblem);

        return UserProblemResultDto.of(userProblemResult);
    }

    private int calculateScore() {
        return random.nextInt(101);
    }

    private ProblemStatus determineStatus(int score) {
        if (score >= 90) {
            return ProblemStatus.PERFECT;
        } else if (score >= 75) {
            return ProblemStatus.GOOD;
        } else if (score >= 50) {
            return ProblemStatus.NORMAL;
        } else if (score >= 25) {
            return ProblemStatus.BAD;
        } else {
            return ProblemStatus.WRONG;
        }
    }
}
