package com.til.controller.problem.Response;

import com.til.domain.problem.dto.ProblemInfoDto;

import lombok.Builder;

@Builder
public record ProblemInfoResponse(
                                  Long id,
                                  String title,
                                  String question,
                                  String solution,
                                  int point,
                                  int level,
                                  int solved,
                                  double percentage,
                                  String categoryName,
                                  String topic
) {

    public static ProblemInfoResponse of(ProblemInfoDto problemInfoDto) {

        return ProblemInfoResponse.builder()
            .id(problemInfoDto.id())
            .title(problemInfoDto.title())
            .question(problemInfoDto.question())
            .solution(problemInfoDto.solution())
            .point(problemInfoDto.point())
            .level(problemInfoDto.level())
            .solved(problemInfoDto.solved())
            .percentage(problemInfoDto.percentage())
            .categoryName(problemInfoDto.categoryName())
            .topic(problemInfoDto.topic())
            .build();
    }
}
