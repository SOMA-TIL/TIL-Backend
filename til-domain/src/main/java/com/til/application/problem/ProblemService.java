package com.til.application.problem;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.domain.auth.dto.AuthUserInfoDto;
import com.til.domain.common.dto.PageParamDto;
import com.til.domain.common.exception.BaseException;
import com.til.domain.problem.dto.FavoriteProblemDto;
import com.til.domain.problem.dto.ProblemOverviewInfoDto;
import com.til.domain.problem.dto.ProblemPageDto;
import com.til.domain.problem.dto.ProblemPublicInfoDto;
import com.til.domain.problem.dto.ProblemSearchDto;
import com.til.domain.problem.enums.ProblemErrorCode;
import com.til.domain.problem.model.Problem;
import com.til.domain.problem.repository.FavoriteProblemRepository;
import com.til.domain.problem.repository.ProblemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final FavoriteProblemRepository favoriteProblemRepository;

    public ProblemPageDto<ProblemOverviewInfoDto> getProblemOverviewList(PageParamDto pageParamDto,
        ProblemSearchDto problemSearchDto) {
        Page<ProblemOverviewInfoDto> problems = problemRepository.getProblemOverviewInfoList(pageParamDto.toPageable(),
            problemSearchDto);
        return ProblemPageDto.of(problems);
    }

    public ProblemPageDto<Problem> getProblemList(PageParamDto pageParamDto) {
        Page<Problem> problems = problemRepository.findAll(pageParamDto.toPageable());
        return ProblemPageDto.of(problems);
    }

    public ProblemPublicInfoDto getProblemInfo(AuthUserInfoDto userInfo, Long problemId) {
        return (userInfo == null) ? getProblemPublicInfo(problemId) : getProblemInfoWithUserData(userInfo.id(),
            problemId);
    }

    private ProblemPublicInfoDto getProblemPublicInfo(Long problemId) {
        return problemRepository.getProblemPublicInfo(problemId);
    }

    private ProblemPublicInfoDto getProblemInfoWithUserData(Long userId, Long problemId) {
        return getProblemPublicInfo(problemId)
            .setFavorite(favoriteProblemRepository.existsByUserIdAndProblemId(userId, problemId));
    }

    @Transactional
    public void toggleFavorite(FavoriteProblemDto favoriteProblemDto) {
        validateProblemExists(favoriteProblemDto.problemId());

        if (favoriteProblemDto.isFavorite()) {
            addFavoriteProblem(favoriteProblemDto);
        } else {
            removeFavoriteProblem(favoriteProblemDto);
        }
    }

    private void validateProblemExists(Long problemId) {
        boolean isExist = problemRepository.existsById(problemId);
        if (!isExist) {
            throw new BaseException(ProblemErrorCode.NOT_FOUND_PROBLEM);
        }
    }

    private void addFavoriteProblem(FavoriteProblemDto favoriteDto) {
        if (favoriteProblemRepository.existsByUserIdAndProblemId(favoriteDto.userId(), favoriteDto.problemId())) {
            throw new BaseException(ProblemErrorCode.ALREADY_FAVORITE_PROBLEM);
        }
        favoriteProblemRepository.save(favoriteDto.toEntity());
    }

    private void removeFavoriteProblem(FavoriteProblemDto favoriteDto) {
        if (!favoriteProblemRepository.existsByUserIdAndProblemId(favoriteDto.userId(), favoriteDto.problemId())) {
            throw new BaseException(ProblemErrorCode.NOT_FOUND_FAVORITE_PROBLEM);
        }
        favoriteProblemRepository.deleteByUserIdAndProblemId(favoriteDto.userId(), favoriteDto.problemId());
    }
}
