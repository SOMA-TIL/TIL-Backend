package com.til.controller.problem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.til.application.problem.ProblemService;
import com.til.common.Page.PageParamRequest;
import com.til.common.Page.PageResponse;
import com.til.common.response.ApiResponse;
import com.til.domain.common.enums.BaseErrorCode;
import com.til.domain.common.exception.BaseException;
import com.til.domain.problem.dto.ProblemListDto;
import com.til.domain.problem.dto.ProblemListInfoDto;
import com.til.domain.problem.enums.ProblemSuccessCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problem")
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping("")
    public ApiResponse<PageResponse<ProblemListInfoDto>> getProblemList(
        @ModelAttribute PageParamRequest pageParamRequest) {
        if (pageParamRequest.page() < 0 || pageParamRequest.size() <= 0) {
            throw new BaseException(BaseErrorCode.INVALID_PAGE_REQUEST);
        }
        ProblemListDto problemListDto = problemService.getProblemList(pageParamRequest.toServiceDto());
        return ApiResponse.ok(ProblemSuccessCode.SUCCESS_GET_PROBLEMS, PageResponse.of(problemListDto.problems(),
            problemListDto.pageInfo()));
    }
}
