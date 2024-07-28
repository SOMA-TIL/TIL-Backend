package com.til.controller.problem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.til.application.problem.ProblemService;
import com.til.common.response.ApiResponse;
import com.til.controller.problem.reponse.ProblemListResponse;
import com.til.domain.problem.dto.ProblemPageDto;

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

        ProblemPageDto problemPageDto = problemService.getProblemList(page, size);
        return ApiResponse.ok(ProblemListResponse.of(problemPageDto));
    }
}
