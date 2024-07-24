package com.til.controller.problem;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.til.application.problem.ProblemService;
import com.til.common.response.ApiResponse;
import com.til.controller.problem.reponse.ProblemListResponse;
import com.til.domain.problem.dto.ProblemListDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problem")
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping("")
    public ApiResponse<ProblemListResponse> getProblemList() {
        List<ProblemListDto> problemList = problemService.getProblemList();
        return ApiResponse.ok(ProblemListResponse.of(problemList));
    }
}
