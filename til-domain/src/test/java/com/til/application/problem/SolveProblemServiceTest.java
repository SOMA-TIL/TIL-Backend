package com.til.application.problem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.til.domain.problem.dto.SolveProblemDto;
import com.til.domain.problem.dto.SolveProblemResultDto;
import com.til.domain.problem.model.Problem;
import com.til.domain.problem.model.ProblemStatus;
import com.til.domain.problem.model.UserProblem;
import com.til.domain.problem.repository.ProblemRepository;
import com.til.domain.problem.repository.UserProblemRepository;
import com.til.domain.user.model.User;
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
        User user = createUser();
        Problem problem = createProblem();
        UserProblem userProblem = createUserProblem(user, problem);

        given(userRepository.getById(anyLong())).willReturn(user);
        given(problemRepository.getById(anyLong())).willReturn(problem);
        given(userProblemRepository.save(any(UserProblem.class))).willReturn(userProblem);

        // when
        SolveProblemResultDto result = solveProblemService.solveProblem(solveProblemDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.status()).isIn(ProblemStatus.values());
    }

    private SolveProblemDto createSolveProblemDto() {
        return SolveProblemDto.builder()
            .userId(1L)
            .problemId(1L)
            .answer("Some Answer")
            .build();
    }

    private User createUser() {
        return User.builder()
            .id(1L)
            .email("test@til.com")
            .nickname("선인장24")
            .build();
    }

    private Problem createProblem() {
        return Problem.builder()
            .id(1L)
            .title("Sample Problem")
            .build();
    }

    private UserProblem createUserProblem(User user, Problem problem) {
        return UserProblem.builder()
            .user(user)
            .problem(problem)
            .status(ProblemStatus.GOOD)
            .build();
    }
}
