package com.til.application.problem;

import java.util.Arrays;
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
        userProblemRepository.save(userProblem);

        // TODO : 채점 요청 후 결과 가져오기
        ProblemStatus status = gradeStatus();
        return SolveProblemStatusDto.of(status);
    }

    // To-do. 채점 로직 수정
    private ProblemStatus gradeStatus() {
        ProblemStatus[] statuses = Arrays.stream(ProblemStatus.values())
            .filter(status -> status != ProblemStatus.PENDING).toArray(ProblemStatus[]::new);
        Random random = new Random();
        int randomIndex = random.nextInt(statuses.length);
        return statuses[randomIndex];
    }
}
