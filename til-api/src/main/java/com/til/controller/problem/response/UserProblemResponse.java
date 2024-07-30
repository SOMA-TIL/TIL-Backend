package com.til.controller.problem.response;

import com.til.domain.problem.dto.UserProblemResultDto;

public record UserProblemResponse(
                                  UserProblemResultDto problemResult
) {

    public static UserProblemResponse of(UserProblemResultDto userProblemResultDto) {
        return new UserProblemResponse(userProblemResultDto);
    }
}
