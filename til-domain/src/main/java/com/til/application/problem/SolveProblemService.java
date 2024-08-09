package com.til.application.problem;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.domain.problem.dto.SolveProblemDto;
import com.til.domain.problem.dto.SolveProblemStatusDto;
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

        return SolveProblemStatusDto.of(userProblem.getId(), userProblem.getStatus());
    }
}
