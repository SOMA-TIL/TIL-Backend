package com.til.application.interview;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.til.domain.common.exception.BaseException;
import com.til.domain.interview.dto.InterviewCodeDto;
import com.til.domain.interview.dto.InterviewCreateDto;
import com.til.domain.interview.enums.InterviewErrorCode;
import com.til.domain.interview.model.InterviewStatus;
import com.til.domain.interview.repository.InterviewRepository;
import com.til.utils.random.RandomValueGenerator;

@ExtendWith(MockitoExtension.class)
public class InterviewServiceTest {

    @InjectMocks
    private InterviewService interviewService;

    @Mock
    private InterviewRepository interviewRepository;

    @Test
    void 모의면접을_정상적으로_생성하고_11자리_code를_받아온다() {
        // given & when
        InterviewCodeDto interviewCodeDto = interviewService.create(createInterviewCreateDto());

        // then
        assertThat(interviewCodeDto.code().length()).isEqualTo(11);
    }

    @Test
    void 모의면접_생성시_code가_중복되면_예외를_던진다() {
        // given
        given(interviewRepository.existsByCode(anyString())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> interviewService.create(createInterviewCreateDto()))
            .isInstanceOf(BaseException.class)
            .extracting(error -> ((BaseException) error).getErrorCode())
            .isEqualTo(InterviewErrorCode.FAIL_CREATE_INTERVIEW);

    }

    private InterviewCreateDto createInterviewCreateDto() {
        return InterviewCreateDto.builder()
            .status(InterviewStatus.PROCESSING)
            .code(RandomValueGenerator.generateRandomId(11))
            .userId(1L)
            .build();
    }

}
