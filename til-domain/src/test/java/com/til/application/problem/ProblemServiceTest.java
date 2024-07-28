package com.til.application.problem;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;

import java.util.Collections;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.til.domain.problem.dto.ProblemPageDto;
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
        PageRequest pageable = PageRequest.of(0, 10);
        Problem problem = createProblem();
        Page<Problem> problemPage = new PageImpl<>(Collections.singletonList(problem));
        given(problemRepository.findAll(any(PageRequest.class))).willReturn(problemPage);

        // when
        ProblemPageDto result = problemService.getProblemList(0, 10);

        // then
        assertThat(result).isNotNull();
        assertThat(result.problems().get(0).id()).isEqualTo(problem.getId());
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
