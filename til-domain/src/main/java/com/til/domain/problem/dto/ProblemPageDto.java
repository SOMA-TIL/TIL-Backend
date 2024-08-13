package com.til.domain.problem.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import com.til.domain.common.dto.PageInfoDto;

import lombok.Builder;

@Builder
public record ProblemPageDto<T>(
                                List<T> problemList,
                                PageInfoDto pageInfo
) {

    public static <T> ProblemPageDto<T> of(Page<T> problems) {
        return ProblemPageDto.<T>builder()
            .problemList(problems.getContent())
            .pageInfo(PageInfoDto.of(problems))
            .build();
    }
}
