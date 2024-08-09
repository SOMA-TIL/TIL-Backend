package com.til.application.interview;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.til.domain.interview.dto.InterviewCodeDto;
import com.til.domain.interview.dto.InterviewCreateDto;
import com.til.domain.interview.model.InterviewStatus;
import com.til.domain.interview.repository.InterviewRepository;

@ExtendWith(MockitoExtension.class)
public class InterviewServiceTest {

    @InjectMocks
    private InterviewService interviewService;

    @Mock
    private InterviewRepository interviewRepository;

    @Test
    void 모의면접을_정상적으로_생성하고_code를_받아온다() {
        // given
        String code = "fresh code";

        // when
        InterviewCodeDto interviewCodeDto = interviewService.create(createInterviewCreateDto(code));

        // then
        assertThat(interviewCodeDto.code()).isEqualTo(code);
    }

    private InterviewCreateDto createInterviewCreateDto(String code) {
        return InterviewCreateDto.builder()
            .status(InterviewStatus.PROCESSING)
            .code(code)
            .userId(1L)
            .build();
    }

}
