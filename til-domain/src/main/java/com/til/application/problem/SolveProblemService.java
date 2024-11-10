package com.til.application.problem;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.common.exception.BaseException;
import com.til.domain.common.dto.PageDto;
import com.til.domain.common.dto.PageParamDto;
import com.til.domain.problem.dto.OthersAnswerDto;
import com.til.domain.problem.dto.SolveProblemDto;
import com.til.domain.problem.dto.SubmitHistoryDto;
import com.til.domain.problem.dto.SubmitResultDto;
import com.til.domain.problem.dto.SubmitStatusDto;
import com.til.domain.problem.enums.ProblemErrorCode;
import com.til.domain.problem.model.UserProblem;
import com.til.domain.problem.repository.ProblemRepository;
import com.til.domain.problem.repository.UserProblemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SolveProblemService {

    private final UserProblemRepository userProblemRepository;
    private final ProblemRepository problemRepository;

    @Transactional
    public SubmitStatusDto solveProblem(SolveProblemDto solveProblemDto) {
        UserProblem userProblem = solveProblemDto.toEntity();
        userProblemRepository.save(userProblem);

        return SubmitStatusDto.of(userProblem.getId(), userProblem.getStatus());
    }

    public SubmitResultDto getProblemSubmitResult(Long userId, Long problemId) {
        validateProblemExists(problemId);
        List<SubmitHistoryDto> submitHistory = userProblemRepository.getSubmitHistory(userId, problemId);
        boolean isPass = userProblemRepository.isProblemPassed(userId, problemId);
        return SubmitResultDto.of(submitHistory, isPass ? problemRepository.getSolutionByProblemId(problemId) : null);
    }

    public PageDto<OthersAnswerDto> getProblemOthersAnswer(Long userId, Long problemId, PageParamDto pageParamDto) {
        validateProblemExists(problemId);
        return PageDto.of(userProblemRepository.getPassedOthersAnswer(problemId, userId, pageParamDto.toPageable()));
    }

    private void validateProblemExists(Long problemId) {
        boolean isExist = problemRepository.existsById(problemId);
        if (!isExist) {
            throw new BaseException(ProblemErrorCode.NOT_FOUND_PROBLEM);
        }
    }
}
