package com.til.application.problem;

import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.domain.problem.dto.SolveProblemDto;
import com.til.domain.problem.dto.SolveProblemStatusDto;
import com.til.domain.problem.model.ProblemStatus;
import com.til.domain.problem.model.UserProblem;
import com.til.domain.problem.repository.ProblemRepository;
import com.til.domain.problem.repository.UserProblemRepository;
import com.til.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SolveProblemService {

    private final UserProblemRepository userProblemRepository;

    private final ProblemRepository problemRepository;

    private final UserRepository userRepository;

    @Transactional
    public SolveProblemStatusDto solveProblem(SolveProblemDto solveProblemDto) {
        UserProblem userProblem = solveProblemDto.toEntity();

        ProblemStatus status = gradeStatus();
        userProblem.setResultStatus(status);
        UserProblem userProblemResult = userProblemRepository.save(userProblem);

        return SolveProblemStatusDto.of(userProblemResult);
    }

    // To-do. 채점 로직 수정
    private ProblemStatus gradeStatus() {
        ProblemStatus[] statuses = ProblemStatus.values();
        Random random = new Random();
        int randomIndex = random.nextInt(statuses.length);
        return statuses[randomIndex];
    }
}
