package com.til.application.problem;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.domain.problem.dto.ProblemInfoDto;
import com.til.domain.problem.model.Problem;
import com.til.domain.problem.repository.ProblemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    @Transactional(readOnly = true)
    public List<ProblemInfoDto> getProblemList() {
        List<Problem> problemList = problemRepository.findAll();
        return problemList.stream()
            .map(ProblemInfoDto::of)
            .collect(Collectors.toList());
    }

}
