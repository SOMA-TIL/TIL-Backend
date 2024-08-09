package com.til.application.problem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.domain.common.dto.PageParamDto;
import com.til.domain.common.exception.BaseException;
import com.til.domain.problem.dto.FavoriteProblemDto;
import com.til.domain.problem.dto.ProblemInfoDto;
import com.til.domain.problem.dto.ProblemPageDto;
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

    public ProblemPageDto getProblemList(PageParamDto pageParamDto) {
        Pageable pageable = pageParamDto.toPageable();
        Page<Problem> problems = problemRepository.findAll(pageable);
        return ProblemPageDto.of(problems);
    }

    public ProblemInfoDto getProblemInfo(Long id) {
        Problem problem = problemRepository.getById(id);
        return ProblemInfoDto.of(problem);
    }

    public ProblemInfoDto getProblemInfoWithUserData(Long userId, Long problemId) {
        Problem problem = problemRepository.getById(problemId);
        return ProblemInfoDto.of(problem, favoriteProblemRepository.existsByUserIdAndProblemId(userId, problemId));
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
