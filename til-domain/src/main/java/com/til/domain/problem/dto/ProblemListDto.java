package com.til.domain.problem.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.til.domain.common.dto.PageInfoDto;
import com.til.domain.problem.model.Problem;

import lombok.Builder;

@Builder
public record ProblemListDto(
                             List<ProblemListInfoDto> problems,
                             PageInfoDto pageInfo
) {

    public static ProblemListDto of(Page<Problem> problemsPage) {
        List<ProblemListInfoDto> problems = problemsPage.getContent().stream()
            .map(ProblemListInfoDto::of)
            .collect(Collectors.toList());
        PageInfoDto pageInfo = PageInfoDto.of(problemsPage);
        return ProblemListDto.builder()
            .problems(problems)
            .pageInfo(pageInfo)
            .build();
    }
}
