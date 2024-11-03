package com.til.application.problem;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.common.exception.BaseException;
import com.til.domain.common.dto.PageParamDto;
import com.til.domain.history.annotation.LogProblemViewHistory;
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

    public ProblemPageDto<ProblemOverviewInfoDto> getProblemOverviewList(Long userId, PageParamDto pageParamDto,
        ProblemSearchDto problemSearchDto) {
        return isGuest(userId) ? getProblemPublicOverviewList(pageParamDto, problemSearchDto)
            : getProblemOverviewListWithUserData(pageParamDto, problemSearchDto, userId);
    }

    public ProblemPageDto<Problem> getProblemList(PageParamDto pageParamDto) {
        Page<Problem> problems = problemRepository.findAll(pageParamDto.toPageable());
        return ProblemPageDto.of(problems);
    }

    @Transactional
    @LogProblemViewHistory
    public ProblemPublicInfoDto getProblemInfo(Long userId, Long problemId) {
        return isGuest(userId) ? getProblemPublicInfo(problemId) : getProblemInfoWithUserData(userId, problemId);
    }

    private ProblemPublicInfoDto getProblemPublicInfo(Long problemId) {
        return problemRepository.getProblemPublicInfo(problemId);
    }

    private ProblemPublicInfoDto getProblemInfoWithUserData(Long userId, Long problemId) {
        return getProblemPublicInfo(problemId)
            .setFavorite(favoriteProblemRepository.existsByUserIdAndProblemId(userId, problemId));
    }

    private ProblemPageDto<ProblemOverviewInfoDto> getProblemPublicOverviewList(PageParamDto pageParamDto,
        ProblemSearchDto problemSearchDto) {
        return ProblemPageDto.of(
            problemRepository.getProblemPublicOverviewInfoList(pageParamDto.toPageable(), problemSearchDto));
    }

    private ProblemPageDto<ProblemOverviewInfoDto> getProblemOverviewListWithUserData(PageParamDto pageParamDto,
        ProblemSearchDto problemSearchDto, Long userId) {
        Page<ProblemOverviewInfoDto> problems = problemRepository.getProblemOverviewListWithUserData(
            pageParamDto.toPageable(), problemSearchDto, userId);
        return ProblemPageDto.of(problems);
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

    private boolean isGuest(Long userId) {
        return userId == null;
    }
}
