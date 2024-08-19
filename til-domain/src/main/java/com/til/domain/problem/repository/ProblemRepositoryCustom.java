package com.til.domain.problem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.til.domain.problem.dto.ProblemOverviewInfoDto;
import com.til.domain.problem.dto.ProblemPublicInfoDto;
import com.til.domain.problem.dto.ProblemSearchDto;

public interface ProblemRepositoryCustom {

    String getSolutionByProblemId(Long problemId);

    Page<ProblemOverviewInfoDto> getProblemPublicOverviewInfoList(Pageable pageable, ProblemSearchDto problemSearchDto);

    Page<ProblemOverviewInfoDto> getProblemOverviewListWithUserData(Pageable pageable,
        ProblemSearchDto problemSearchDto, Long userId);

    ProblemPublicInfoDto getProblemPublicInfo(Long problemId);
}
