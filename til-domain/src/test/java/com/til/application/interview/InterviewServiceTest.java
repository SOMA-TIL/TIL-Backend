package com.til.application.interview;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.til.common.exception.BaseException;
import com.til.common.utils.random.RandomValueGenerator;
import com.til.domain.category.repository.InterviewCategoryRepository;
import com.til.domain.category.repository.ProblemCategoryRepository;
import com.til.domain.interview.dto.InterviewCodeDto;
import com.til.domain.interview.dto.InterviewCreateDto;
import com.til.domain.interview.dto.InterviewSolveDto;
import com.til.domain.interview.enums.InterviewErrorCode;
import com.til.domain.interview.model.Interview;
import com.til.domain.interview.model.InterviewStatus;
import com.til.domain.interview.model.InterviewType;
import com.til.domain.interview.repository.InterviewProblemRepository;
import com.til.domain.interview.repository.InterviewRepository;
import com.til.domain.problem.repository.ProblemRepository;

@ExtendWith(MockitoExtension.class)
public class InterviewServiceTest {

    @InjectMocks
    private InterviewService interviewService;

    @Mock
    private InterviewRepository interviewRepository;

    @Mock
    private InterviewCategoryRepository interviewCategoryRepository;

    @Mock
    private InterviewProblemRepository interviewProblemRepository;

    @Mock
    private ProblemRepository problemRepository;

    @Mock
    private ProblemCategoryRepository problemCategoryRepository;

    @Test
    void 모의면접을_정상적으로_생성하고_11자리_code를_받아온다() {
        // given & when
        InterviewCodeDto interviewCodeDto = interviewService.createInterview(createInterviewCreateDto(
            InterviewType.NORMAL, InterviewStatus.PROCESSING));

        // then
        assertThat(interviewCodeDto.code().length()).isEqualTo(11);
    }

    @Test
    void 모의면접_생성시_code가_중복되면_예외를_던진다() {
        // given
        given(interviewRepository.existsByCode(anyString())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> interviewService.createInterview(createInterviewCreateDto(InterviewType.NORMAL,
            InterviewStatus.PROCESSING)))
            .isInstanceOf(BaseException.class)
            .extracting(error -> ((BaseException) error).getErrorCode())
            .isEqualTo(InterviewErrorCode.FAIL_CREATE_INTERVIEW);
    }

    @Test
    void 모의면접_생성시_이미_진행중인_모의면접이_존재하면_예외를_던진다() {
        // given
        given(interviewRepository.existsByUserIdAndCreatingOrProcessingStatus(anyLong())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> interviewService.createInterview(createInterviewCreateDto(InterviewType.NORMAL,
            InterviewStatus.CREATING)))
            .isInstanceOf(BaseException.class)
            .extracting(error -> ((BaseException) error).getErrorCode())
            .isEqualTo(InterviewErrorCode.ALREADY_PROCESSING_INTERVIEW);
    }

    @Test
    void 경험_기반_모의면접_생성시_이미_생성중인_모의면접이_존재하면_예외를_던진다() {
        // given
        given(interviewRepository.existsByUserIdAndCreatingOrProcessingStatus(anyLong())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> interviewService.createInterview(createInterviewCreateDto(InterviewType.PORTFOLIO,
            InterviewStatus.CREATING)))
            .isInstanceOf(BaseException.class)
            .extracting(error -> ((BaseException) error).getErrorCode())
            .isEqualTo(InterviewErrorCode.ALREADY_PROCESSING_INTERVIEW);
    }

    @Test
    void 경험_기반_모의면접_생성시_이미_진행중인_모의면접이_존재하면_예외를_던진다() {
        // given
        given(interviewRepository.existsByUserIdAndCreatingOrProcessingStatus(anyLong())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> interviewService.createInterview(createInterviewCreateDto(InterviewType.PORTFOLIO,
            InterviewStatus.PROCESSING)))
            .isInstanceOf(BaseException.class)
            .extracting(error -> ((BaseException) error).getErrorCode())
            .isEqualTo(InterviewErrorCode.ALREADY_PROCESSING_INTERVIEW);
    }

    @Test
    void 진행중인_모의면접이_존재하지_않으면_예외를_던진다() {
        // given
        given(interviewRepository.getProcessingInterview(anyLong(), anyString())).willThrow(new BaseException(
            InterviewErrorCode.NOT_FOUND_INTERVIEW));

        // when & then
        assertThatThrownBy(() -> interviewService.getProcessingInterviewInfo(anyLong(), anyString()))
            .isInstanceOf(BaseException.class)
            .extracting(error -> ((BaseException) error).getErrorCode())
            .isEqualTo(InterviewErrorCode.NOT_FOUND_INTERVIEW);
    }

    @Test
    void 유효하지_않은_모의면접에_답변을_제출하면_예외를_던진다() {
        // given
        given(interviewRepository.getProcessingInterview(anyLong(), anyString()))
            .willThrow(new BaseException(InterviewErrorCode.NOT_FOUND_INTERVIEW));

        InterviewSolveDto interviewSolveDto = createInterviewSolveDto("code", 1, 1L);

        // when & then
        assertThatThrownBy(() -> interviewService.solveInterviewProblem(interviewSolveDto))
            .isInstanceOf(BaseException.class)
            .extracting(error -> ((BaseException) error).getErrorCode())
            .isEqualTo(InterviewErrorCode.NOT_FOUND_INTERVIEW);
    }

    @Test
    void 유효하지_않은_면접_문제에_답변을_제출하면_예외를_던진다() {
        // given
        given(interviewRepository.getProcessingInterview(anyLong(), anyString())).willReturn(
            createInterview(InterviewStatus.PROCESSING, InterviewType.NORMAL)
        );
        given(interviewProblemRepository.existsBySolvable(anyLong(), anyInt(), any()))
            .willThrow(new BaseException(InterviewErrorCode.NOT_FOUND_INTERVIEW_PROBLEM));

        InterviewSolveDto interviewSolveDto = createInterviewSolveDto("code", 1, 1L);

        // when & then
        assertThatThrownBy(() -> interviewService.solveInterviewProblem(interviewSolveDto))
            .isInstanceOf(BaseException.class)
            .extracting(error -> ((BaseException) error).getErrorCode())
            .isEqualTo(InterviewErrorCode.NOT_FOUND_INTERVIEW_PROBLEM);
    }

    @Test
    void 면접_문제풀이의_순서_일관성이_깨지면_예외를_던진다() {
        // given
        given(interviewRepository.getProcessingInterview(anyLong(), anyString())).willReturn(
            createInterview(InterviewStatus.PROCESSING, InterviewType.NORMAL)
        );
        given(interviewProblemRepository.existsBySolvable(anyLong(), anyInt(), any())).willReturn(true);
        given(interviewProblemRepository.existsBySequenceConsistency(anyLong(), anyInt(), any())).willThrow(
            new BaseException(InterviewErrorCode.INTERVIEW_SEQUENCE_INCONSISTENCY));

        InterviewSolveDto interviewSolveDto = createInterviewSolveDto("code", 1, 1L);

        // when & then
        assertThatThrownBy(() -> interviewService.solveInterviewProblem(interviewSolveDto))
            .isInstanceOf(BaseException.class)
            .extracting(error -> ((BaseException) error).getErrorCode())
            .isEqualTo(InterviewErrorCode.INTERVIEW_SEQUENCE_INCONSISTENCY);
    }

    @Test
    void 면접_문제를_모두_풀이하지_않았는데_면접을_완료하면_예외를_던진다() {
        // given
        given(interviewRepository.getProcessingInterview(anyLong(), anyString())).willReturn(
            createInterview(InterviewStatus.PROCESSING, InterviewType.NORMAL)
        );
        given(interviewProblemRepository.existsByInterviewIdAndStatus(anyLong(), any())).willThrow(new BaseException(
            InterviewErrorCode.FAIL_SUBMIT_INTERVIEW));

        // when & then
        assertThatThrownBy(() -> interviewService.submitInterview(anyLong(), anyString()))
            .isInstanceOf(BaseException.class)
            .extracting(error -> ((BaseException) error).getErrorCode())
            .isEqualTo(InterviewErrorCode.FAIL_SUBMIT_INTERVIEW);
    }

    private Interview createInterview(InterviewStatus status, InterviewType type) {
        return Interview.builder()
            .id(1L)
            .type(type)
            .code("code")
            .status(status)
            .userId(1L)
            .build();
    }

    private InterviewCreateDto createInterviewCreateDto(InterviewType type, InterviewStatus status) {
        return InterviewCreateDto.builder()
            .interviewType(type)
            .status(status)
            .code(RandomValueGenerator.generateRandomString(11))
            .questionSize(3)
            .userId(1L)
            .portfolio("123")
            .categoryIdList(List.of(1L))
            .build();
    }

    private InterviewSolveDto createInterviewSolveDto(
        String code, Integer sequence, Long userId
    ) {
        return InterviewSolveDto.builder()
            .code(code)
            .sequence(sequence)
            .answer("random userAnswer")
            .userId(userId)
            .build();
    }

}
