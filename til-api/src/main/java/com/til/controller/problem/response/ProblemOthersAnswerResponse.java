package com.til.controller.problem.response;

import java.util.List;

import com.til.domain.common.dto.PageDto;
import com.til.domain.common.dto.PageInfoDto;
import com.til.domain.problem.dto.OthersAnswerDto;

import lombok.Builder;

@Builder
public record ProblemOthersAnswerResponse(
                                          List<OthersAnswerDto> answerList,
                                          PageInfoDto pageInfo
) {

    public static ProblemOthersAnswerResponse of(PageDto<OthersAnswerDto> othersAnswerList) {
        return ProblemOthersAnswerResponse.builder()
            .answerList(othersAnswerList.list())
            .pageInfo(othersAnswerList.pageInfo())
            .build();
    }
}
