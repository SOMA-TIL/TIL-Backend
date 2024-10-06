package com.til.controller.interview.request;

import com.til.domain.interview.dto.SpeechInterviewCreateDto;

public record SpeechInterviewCreateRequest(
                                           int questionSize,
                                           String portfolio
) {

    public SpeechInterviewCreateDto toServiceDto(Long userId) {
        return SpeechInterviewCreateDto.builder()
            .questionSize(questionSize)
            .portfolio(portfolio)
            .userId(userId)
            .build();
    }
}
