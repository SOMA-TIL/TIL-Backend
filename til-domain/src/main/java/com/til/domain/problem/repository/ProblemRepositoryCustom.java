package com.til.domain.problem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.til.domain.problem.dto.ProblemOverviewInfoDto;

public interface ProblemRepositoryCustom {

    String getSolutionByProblemId(Long problemId);

    Page<ProblemOverviewInfoDto> getProblemOverviewInfoList(Pageable pageable);

}
