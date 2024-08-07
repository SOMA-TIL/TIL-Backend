package com.til.application.interview;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.til.domain.interview.dto.InterviewCreateDto;
import com.til.domain.interview.dto.InterviewUuidDto;
import com.til.domain.interview.model.InterviewStatus;
import com.til.domain.interview.repository.InterviewRepository;

@ExtendWith(MockitoExtension.class)
public class InterviewServiceTest {

    @InjectMocks
    private InterviewService interviewService;

    @Mock
    private InterviewRepository interviewRepository;

    @Test
    void 모의면접을_정상적으로_생성하고_uuid를_받아온다() {
        // given
        String uuid = "fresh uuid";

        // when
        InterviewUuidDto interviewUuidDto = interviewService.create(createInterviewCreateDto(uuid));

        // then
        assertThat(interviewUuidDto.uuid()).isEqualTo(uuid);
    }

    private InterviewCreateDto createInterviewCreateDto(String uuid) {
        return InterviewCreateDto.builder()
            .status(InterviewStatus.PROCESSING)
            .uuid(uuid)
            .userId(1L)
            .build();
    }

}
