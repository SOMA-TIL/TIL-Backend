package com.til.application.problem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.til.common.exception.BaseException;
import com.til.domain.grading.enums.GradingStatus;
import com.til.domain.problem.dto.SolveProblemDto;
import com.til.domain.problem.dto.SubmitResultDto;
import com.til.domain.problem.dto.SubmitStatusDto;
import com.til.domain.problem.enums.ProblemErrorCode;
import com.til.domain.problem.model.UserProblem;
import com.til.domain.problem.repository.ProblemRepository;
import com.til.domain.problem.repository.UserProblemRepository;

@ExtendWith(MockitoExtension.class)
class SolveProblemServiceTest {

    @InjectMocks
    private SolveProblemService solveProblemService;

    @Mock
    private UserProblemRepository userProblemRepository;

    @Mock
    private ProblemRepository problemRepository;

    @Test
    void 문제풀이시_상태_결과를_반환한다() {
        // given
        SolveProblemDto solveProblemDto = createSolveProblemDto();
        UserProblem userProblem = createUserProblem(1L, 1L);

        given(userProblemRepository.save(any(UserProblem.class))).willReturn(userProblem);

        // when
        SubmitStatusDto result = solveProblemService.solveProblem(solveProblemDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.status()).isEqualTo(GradingStatus.PENDING);
    }

    @Test
    void 존재하지_않는_문제의_히스토리를_가져올_때_에러가_발생한다() {
        // given
        given(problemRepository.existsById(1L)).willReturn(false);

        // when & then
        assertThatThrownBy(() -> solveProblemService.getProblemSubmitResult(1L, 1L))
            .isInstanceOf(BaseException.class)
            .extracting(error -> ((BaseException) error).getErrorCode())
            .isEqualTo(ProblemErrorCode.NOT_FOUND_PROBLEM);
    }

    @Test
    void 제출한_답변_중_한번도_통과하지_못한_경우_모범답안_정보를_가져올_수_없다() {
        // given
        given(problemRepository.existsById(1L)).willReturn(true);
        given(userProblemRepository.getSubmitHistory(1L, 1L)).willReturn(null);
        given(userProblemRepository.isProblemPassed(1L, 1L)).willReturn(false);

        // when & then
        assertThat(solveProblemService.getProblemSubmitResult(1L, 1L))
            .isNotNull()
            .extracting(SubmitResultDto::solution)
            .isNull();
    }

    private SolveProblemDto createSolveProblemDto() {
        return SolveProblemDto.builder()
            .userId(1L)
            .problemId(1L)
            .answer("Some Answer")
            .build();
    }

    private UserProblem createUserProblem(Long userId, Long problemId) {
        return UserProblem.builder()
            .userId(userId)
            .problemId(problemId)
            .status(GradingStatus.COMPLETED)
            .build();
    }
}
