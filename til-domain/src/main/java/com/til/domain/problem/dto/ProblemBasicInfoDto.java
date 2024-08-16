package com.til.domain.problem.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record ProblemBasicInfoDto(
                                  Long id,
                                  String title,
                                  Integer level
) {

    public static List<Long> getIdList(List<ProblemBasicInfoDto> problemBasicInfoDtoList) {
        return problemBasicInfoDtoList.stream().map(ProblemBasicInfoDto::id).toList();
    }

}
