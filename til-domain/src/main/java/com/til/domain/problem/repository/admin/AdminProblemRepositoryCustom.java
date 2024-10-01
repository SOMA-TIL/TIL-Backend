package com.til.domain.problem.repository.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.til.domain.problem.dto.AdminProblemInfoDto;
import com.til.domain.problem.dto.AdminProblemListDto;
import com.til.domain.problem.dto.AdminUpdateProblemDto;
import com.til.domain.problem.dto.ProblemSearchDto;

public interface AdminProblemRepositoryCustom {

    void updateProblem(AdminUpdateProblemDto adminUpdateProblemDto);

    AdminProblemInfoDto getProblemInfo(Long problemId);

    Page<AdminProblemListDto> getProblemList(Pageable pageable, ProblemSearchDto problemSearchDto);
}
