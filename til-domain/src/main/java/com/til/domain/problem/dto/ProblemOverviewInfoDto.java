package com.til.domain.problem.dto;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.Tuple;

import lombok.Builder;

@Builder
public record ProblemOverviewInfoDto(
                                     Long id,
                                     String title,
                                     Integer level,
                                     List<Long> categoryList,
                                     @JsonInclude(JsonInclude.Include.NON_NULL) ProblemUserStatusDto userStatus
) {

    public static ProblemOverviewInfoDto of(ProblemBasicInfoDto problemBasicInfoDto, List<Long> categoryList) {
        return ProblemOverviewInfoDto.builder()
            .id(problemBasicInfoDto.id())
            .title(problemBasicInfoDto.title())
            .level(problemBasicInfoDto.level())
            .categoryList(categoryList)
            .build();
    }

    public static ProblemOverviewInfoDto of(ProblemBasicInfoDto problemBasicInfoDto, List<Long> categoryList,
        ProblemUserStatusDto userStatus) {
        return ProblemOverviewInfoDto.builder()
            .id(problemBasicInfoDto.id())
            .title(problemBasicInfoDto.title())
            .level(problemBasicInfoDto.level())
            .categoryList(categoryList)
            .userStatus(userStatus)
            .build();
    }

    public static List<ProblemOverviewInfoDto> ofList(List<ProblemBasicInfoDto> problemBasicInfoDtoList,
        Map<Long, List<Long>> categoryInfo) {
        return problemBasicInfoDtoList.stream()
            .map(p -> ProblemOverviewInfoDto.of(p, categoryInfo.get(p.id()))).toList();
    }

    public static List<ProblemOverviewInfoDto> ofListFromTuple(List<Tuple> tupleList,
        Map<Long, List<Long>> categoryInfo) {
        return tupleList.stream()
            .map(tuple -> {
                ProblemBasicInfoDto basicInfo = Objects.requireNonNull(tuple.get(0, ProblemBasicInfoDto.class));
                ProblemUserStatusDto userStatus = Objects.requireNonNull(tuple.get(1, ProblemUserStatusDto.class));
                return ProblemOverviewInfoDto.of(basicInfo, categoryInfo.get(basicInfo.id()), userStatus);
            })
            .toList();
    }
}
