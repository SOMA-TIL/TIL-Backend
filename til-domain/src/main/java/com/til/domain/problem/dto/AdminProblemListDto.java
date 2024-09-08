package com.til.domain.problem.dto;

import java.util.List;
import java.util.Map;

import lombok.Builder;

@Builder
public record AdminProblemListDto(
                                  Long id,
                                  String title,
                                  Integer level,
                                  List<Long> categoryList,
                                  Long finishCount,
                                  Float passRate
) {

    public static AdminProblemListDto of(ProblemBasicInfoDto problemBasicInfoDto, List<Long> categoryList) {
        return AdminProblemListDto.builder()
            .id(problemBasicInfoDto.id())
            .title(problemBasicInfoDto.title())
            .level(problemBasicInfoDto.level())
            .finishCount(problemBasicInfoDto.finishCount())
            .passRate(problemBasicInfoDto.passRate())
            .categoryList(categoryList)
            .build();
    }

    public static List<AdminProblemListDto> ofList(List<ProblemBasicInfoDto> problemBasicInfoDtoList,
        Map<Long, List<Long>> categoryInfo) {
        return problemBasicInfoDtoList.stream()
            .map(p -> AdminProblemListDto.of(p, categoryInfo.get(p.id()))).toList();
    }
}
