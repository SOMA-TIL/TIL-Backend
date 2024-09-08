package com.til.controller.problem.response;

import java.util.List;

import com.til.domain.common.dto.PageInfoDto;
import com.til.domain.problem.dto.AdminProblemListDto;

import lombok.Builder;

@Builder
public record ProblemPageResponse(
                                  List<AdminProblemListDto> problemList,
                                  PageInfoDto pageInfo
) {

    public static ProblemPageResponse of(List<AdminProblemListDto> problemList, PageInfoDto pageInfo) {
        return ProblemPageResponse.builder()
            .problemList(problemList)
            .pageInfo(pageInfo)
            .build();
    }
}
