package com.til.domain.problem.dto;

import java.util.List;
import java.util.Map;

import lombok.Builder;

@Builder
public record ProblemOverviewInfoDto(
                                     Long id,
                                     String title,
                                     Integer level,
                                     List<Long> categoryList
) {

    public static ProblemOverviewInfoDto of(ProblemBasicInfoDto problemBasicInfoDto, List<Long> categoryList) {
        return new ProblemOverviewInfoDto(problemBasicInfoDto.id(), problemBasicInfoDto.title(),
            problemBasicInfoDto.level(), categoryList);
    }

    public static List<ProblemOverviewInfoDto> ofList(List<ProblemBasicInfoDto> problemBasicInfoDtoList,
        Map<Long, List<Long>> categoryInfo) {
        return problemBasicInfoDtoList.stream()
            .map(p -> ProblemOverviewInfoDto.of(p, categoryInfo.get(p.id()))).toList();
    }

}
