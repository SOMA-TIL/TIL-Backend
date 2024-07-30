package com.til.controller.problem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.til.application.problem.ProblemService;
import com.til.application.userProblem.UserProblemService;
import com.til.common.annotation.CurrentUser;
import com.til.common.page.PageParamRequest;
import com.til.common.page.PageResponse;
import com.til.common.response.ApiResponse;
import com.til.controller.problem.request.FavoriteProblemRequest;
import com.til.controller.problem.request.UserProblemRequest;
import com.til.controller.problem.response.ProblemInfoResponse;
import com.til.controller.problem.response.UserProblemResponse;
import com.til.domain.common.enums.BaseErrorCode;
import com.til.domain.common.exception.BaseException;
import com.til.domain.problem.dto.ProblemInfoDto;
import com.til.domain.problem.dto.ProblemListDto;
import com.til.domain.problem.dto.ProblemListInfoDto;
import com.til.domain.problem.dto.UserProblemResultDto;
import com.til.domain.problem.enums.ProblemSuccessCode;
import com.til.domain.user.dto.UserInfoDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problem")
public class ProblemController {

    private final ProblemService problemService;
    private final UserProblemService userProblemService;

    @GetMapping("")
    public ApiResponse<PageResponse<ProblemListInfoDto>> getProblemList(
        @ModelAttribute PageParamRequest pageParamRequest) {
        if (pageParamRequest.page() < 0 || pageParamRequest.size() <= 0) {
            throw new BaseException(BaseErrorCode.INVALID_PAGE_REQUEST);
        }
        ProblemListDto problemListDto = problemService.getProblemList(pageParamRequest.toServiceDto());
        return ApiResponse.ok(ProblemSuccessCode.SUCCESS_GET_PROBLEM_LIST, PageResponse.of(problemListDto.problems(),
            problemListDto.pageInfo()));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProblemInfoResponse> getProblemInfo(@PathVariable Long id) {
        ProblemInfoDto problemInfoDto = problemService.getProblemInfo(id);
        return ApiResponse.ok(ProblemSuccessCode.SUCCESS_GET_PROBLEM_INFO, ProblemInfoResponse.of(problemInfoDto));
    }

    @PostMapping("/{id}/favorite")
    public ApiResponse<Void> favoriteProblem(@CurrentUser UserInfoDto userInfo, @PathVariable Long id,
        @RequestBody FavoriteProblemRequest favoriteProblemRequest) {
        problemService.toggleFavorite(favoriteProblemRequest.toServiceDto(userInfo.id(), id));
        return ApiResponse.ok();
    }

    @PostMapping("/{id}/solve")
    public ApiResponse<UserProblemResponse> submitAnswer(@CurrentUser UserInfoDto userInfo, @PathVariable Long id,
        @RequestBody @Valid UserProblemRequest userProblemRequest) {
        UserProblemResultDto userProblemResultDto = userProblemService.solveProblem(userProblemRequest.toServiceDto(
            userInfo.id(), id));
        return ApiResponse.ok(ProblemSuccessCode.SUCCESS_SOLVE_PROBLEM, UserProblemResponse.of(userProblemResultDto));
    }
}
