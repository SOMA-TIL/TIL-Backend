package com.til.domain.problem.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.til.domain.common.dto.PageInfoDto;
import com.til.domain.problem.model.Problem;

import lombok.Builder;

@Builder
public record ProblemPageDto(
                             List<ProblemListInfoDto> problemList,
                             PageInfoDto pageInfo
) {

    public static ProblemPageDto of(Page<Problem> problemsPage) {
        List<ProblemListInfoDto> problemListInfoDtoList = problemsPage.getContent().stream()
            .map(ProblemListInfoDto::of)
            .collect(Collectors.toList());
        PageInfoDto pageInfo = PageInfoDto.of(problemsPage);
        return ProblemPageDto.builder()
            .problemList(problemListInfoDtoList)
            .pageInfo(pageInfo)
            .build();
    }
}
