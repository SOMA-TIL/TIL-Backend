package com.til.controller.problem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.til.application.problem.ProblemService;
import com.til.common.response.ApiResponse;
import com.til.controller.problem.reponse.ProblemListResponse;
import com.til.domain.common.exception.BaseException;
import com.til.domain.problem.dto.ProblemPageDto;
import com.til.domain.problem.enums.ProblemErrorCode;
import com.til.domain.problem.enums.ProblemSuccessCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problem")
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping("")
    public ApiResponse<ProblemListResponse> getProblemList(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

        if (page < 0 || size <= 0) {
            throw new BaseException(ProblemErrorCode.INVALID_PAGE_REQUEST);
        }
        ProblemPageDto problemPageDto = problemService.getProblemList(page, size);
        return ApiResponse.ok(ProblemSuccessCode.SUCCESS_GET_PROBLEMS, ProblemListResponse.of(problemPageDto));
    }
}
