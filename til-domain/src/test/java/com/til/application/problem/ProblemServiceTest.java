package com.til.application.problem;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.til.domain.common.dto.PageParamDto;
import com.til.domain.common.exception.BaseException;
import com.til.domain.problem.dto.ProblemInfoDto;
import com.til.domain.problem.dto.ProblemListDto;
import com.til.domain.problem.enums.ProblemErrorCode;
import com.til.domain.problem.model.Problem;
import com.til.domain.problem.repository.ProblemRepository;

@ExtendWith(MockitoExtension.class)
public class ProblemServiceTest {

    @InjectMocks
    private ProblemService problemService;

    @Mock
    private ProblemRepository problemRepository;

    @Test
    void 문제리스트를_정상적으로_반환한다() {
        // given
        PageParamDto pageParamDto = PageParamDto.builder()
            .page(0)
            .size(10)
            .sort("id")
            .order("asc")
            .build();

        Problem problem = createProblem();
        Page<Problem> problemPage = new PageImpl<>(Collections.singletonList(problem), PageRequest.of(0, 10), 1);
        given(problemRepository.findAll(any(PageRequest.class))).willReturn(problemPage);

        // when
        ProblemListDto result = problemService.getProblemList(pageParamDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.problems().get(0).id()).isEqualTo(problem.getId());
        assertThat(result.problems().get(0).title()).isEqualTo(problem.getTitle());
    }

    @Test
    void 문제_상세정보를_정상적으로_반환한다() {
        // given
        Long problemId = 1L;
        Problem problem = createProblem();
        given(problemRepository.findById(problemId)).willReturn(Optional.of(problem));

        // when
        ProblemInfoDto result = problemService.getProblemInfo(problemId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(problem.getId());
        assertThat(result.title()).isEqualTo(problem.getTitle());
    }

    @Test
    void 문제를_찾지_못했을_경우_예외를_던진다() {
        // given
        Long problemId = 1L;
        given(problemRepository.findById(problemId)).willReturn(Optional.empty());

        // when
        Throwable thrown = catchThrowable(() -> problemService.getProblemInfo(problemId));

        // then
        assertThat(thrown).isInstanceOf(BaseException.class)
            .hasMessage(ProblemErrorCode.NOT_FOUND_PROBLEM.getMessage());
    }

    private Problem createProblem() {
        return Problem.builder()
            .id(1L)
            .title("Sample Problem")
            .question("Sample Question")
            .solution("Sample Solution")
            .point(10)
            .level(1)
            .solved(100)
            .percentage(90.0f)
            .problemCategorySet(new HashSet<>())
            .build();
    }

}
