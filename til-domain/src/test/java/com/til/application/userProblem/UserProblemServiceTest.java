package com.til.application.userProblem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.til.domain.common.exception.BaseException;
import com.til.domain.problem.dto.UserProblemDto;
import com.til.domain.problem.dto.UserProblemResultDto;
import com.til.domain.problem.enums.ProblemErrorCode;
import com.til.domain.problem.model.Problem;
import com.til.domain.problem.model.ProblemStatus;
import com.til.domain.problem.model.UserProblem;
import com.til.domain.problem.repository.ProblemRepository;
import com.til.domain.problem.repository.UserProblemRepository;
import com.til.domain.user.model.User;
import com.til.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserProblemServiceTest {

    @InjectMocks
    private UserProblemService userProblemService;

    @Mock
    private UserProblemRepository userProblemRepository;

    @Mock
    private ProblemRepository problemRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void 문제풀이시_저장에_실패하면_예외를_던진다() {
        // given
        UserProblemDto userProblemDto = createUserProblemDto();
        given(userRepository.getById(anyLong())).willReturn(createUser());
        given(problemRepository.getById(anyLong())).willReturn(createProblem());
        given(userProblemRepository.saveUserProblem(any(UserProblem.class))).willThrow(new BaseException(
            ProblemErrorCode.FAILED_TO_SAVE_PROBLEM));

        // when & then
        assertThatThrownBy(() -> userProblemService.solveProblem(userProblemDto))
            .isInstanceOf(BaseException.class)
            .extracting(error -> ((BaseException) error).getErrorCode())
            .isEqualTo(ProblemErrorCode.FAILED_TO_SAVE_PROBLEM);
    }

    @Test
    void 문제풀이시_문제를_풀고_상태결과를_반환한다() {
        // given
        UserProblemDto userProblemDto = createUserProblemDto();
        User user = createUser();
        Problem problem = createProblem();
        UserProblem userProblem = createUserProblem(user, problem);

        given(userRepository.getById(anyLong())).willReturn(user);
        given(problemRepository.getById(anyLong())).willReturn(problem);
        given(userProblemRepository.saveUserProblem(any(UserProblem.class))).willReturn(userProblem);

        // when
        UserProblemResultDto result = userProblemService.solveProblem(userProblemDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.status()).isIn(ProblemStatus.values());
    }

    private UserProblemDto createUserProblemDto() {
        return UserProblemDto.builder()
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
            .score(85)
            .status(ProblemStatus.GOOD)
            .build();
    }
}
