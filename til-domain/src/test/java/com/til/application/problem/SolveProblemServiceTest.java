package com.til.application.problem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.til.domain.problem.dto.SolveProblemDto;
import com.til.domain.problem.dto.SolveProblemModalDto;
import com.til.domain.problem.model.ProblemStatus;
import com.til.domain.problem.model.UserProblem;
import com.til.domain.problem.repository.ProblemRepository;
import com.til.domain.problem.repository.UserProblemRepository;
import com.til.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class SolveProblemServiceTest {

    @InjectMocks
    private SolveProblemService solveProblemService;

    @Mock
    private UserProblemRepository userProblemRepository;

    @Mock
    private ProblemRepository problemRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void 문제풀이시_상태_결과를_반환한다() {
        // given
        SolveProblemDto solveProblemDto = createSolveProblemDto();
        UserProblem userProblem = createUserProblem(1L, 1L);

        given(userProblemRepository.save(any(UserProblem.class))).willReturn(userProblem);

        // when
        SolveProblemModalDto result = solveProblemService.solveProblem(solveProblemDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.status()).isEqualTo(ProblemStatus.PASS);
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
            .status(ProblemStatus.PASS)
            .build();
    }
}
