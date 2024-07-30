package com.til.application.problem;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.til.domain.common.dto.PageParamDto;
import com.til.domain.common.exception.BaseException;
import com.til.domain.problem.dto.FavoriteProblemDto;
import com.til.domain.problem.dto.ProblemInfoDto;
import com.til.domain.problem.dto.ProblemListDto;
import com.til.domain.problem.enums.ProblemErrorCode;
import com.til.domain.problem.model.Problem;
import com.til.domain.problem.repository.FavoriteProblemRepository;
import com.til.domain.problem.repository.ProblemRepository;

@ExtendWith(MockitoExtension.class)
public class ProblemServiceTest {

    @InjectMocks
    private ProblemService problemService;

    @Mock
    private ProblemRepository problemRepository;

    @Mock
    private FavoriteProblemRepository favoriteProblemRepository;

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
        given(problemRepository.getById(problemId)).willReturn(problem);

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
        given(problemRepository.getById(problemId)).willThrow(new BaseException(ProblemErrorCode.NOT_FOUND_PROBLEM));

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

    @TestFactory
    public Collection<DynamicTest> 문제_즐겨찾기_추가_테스트를_진행한다() {
        FavoriteProblemDto favoriteProblemDto = createFavoriteProblemDto(true);
        given(problemRepository.existsById(favoriteProblemDto.problemId())).willReturn(true);

        return List.of(
            dynamicTest("새롭게 문제를 즐겨찾기에 추가한다", () -> {
                // given
                given(favoriteProblemRepository.existsByUserIdAndProblemId(favoriteProblemDto.userId(),
                    favoriteProblemDto.problemId())).willReturn(false);

                // when
                problemService.toggleFavorite(favoriteProblemDto);

                // then
                then(favoriteProblemRepository).should().save(any());
            }),
            dynamicTest("이미 즐겨찾기한 문제를 다시 추가할 경우 예외를 던진다", () -> {
                // given
                given(favoriteProblemRepository.existsByUserIdAndProblemId(favoriteProblemDto.userId(),
                    favoriteProblemDto.problemId())).willReturn(true);

                // when & then
                assertThatThrownBy(() -> problemService.toggleFavorite(favoriteProblemDto))
                    .isInstanceOf(BaseException.class)
                    .extracting(error -> ((BaseException) error).getErrorCode())
                    .isEqualTo(ProblemErrorCode.ALREADY_FAVORITE_PROBLEM);
            }),
            dynamicTest("존재하지 않는 문제를 즐겨찾기에 추가할 경우 예외를 던진다", () -> {
                // given
                given(problemRepository.existsById(favoriteProblemDto.problemId())).willReturn(false);

                // when & then
                assertThatThrownBy(() -> problemService.toggleFavorite(favoriteProblemDto))
                    .isInstanceOf(BaseException.class)
                    .extracting(error -> ((BaseException) error).getErrorCode())
                    .isEqualTo(ProblemErrorCode.NOT_FOUND_PROBLEM);
            })
        );
    }

    @TestFactory
    public Collection<DynamicTest> 문제_즐겨찾기_삭제_테스트를_진행한다() {
        FavoriteProblemDto favoriteProblemDto = createFavoriteProblemDto(false);
        given(problemRepository.existsById(favoriteProblemDto.problemId())).willReturn(true);

        return List.of(
            dynamicTest("즐겨찾기한 문제를 삭제한다", () -> {
                // given
                given(favoriteProblemRepository.existsByUserIdAndProblemId(favoriteProblemDto.userId(),
                    favoriteProblemDto.problemId())).willReturn(true);

                // when
                problemService.toggleFavorite(favoriteProblemDto);

                // then
                then(favoriteProblemRepository)
                    .should().deleteByUserIdAndProblemId(favoriteProblemDto.userId(), favoriteProblemDto.problemId());
            }),
            dynamicTest("즐겨찾기하지 않은 문제를 삭제할 경우 예외를 던진다", () -> {
                // given
                given(favoriteProblemRepository.existsByUserIdAndProblemId(favoriteProblemDto.userId(),
                    favoriteProblemDto.problemId())).willReturn(false);

                // when & then
                assertThatThrownBy(() -> problemService.toggleFavorite(favoriteProblemDto))
                    .isInstanceOf(BaseException.class)
                    .extracting(error -> ((BaseException) error).getErrorCode())
                    .isEqualTo(ProblemErrorCode.NOT_FOUND_FAVORITE_PROBLEM);
            }),
            dynamicTest("존재하지 않는 문제를 즐겨찾기에서 삭제할 경우 예외를 던진다", () -> {
                // given
                given(problemRepository.existsById(favoriteProblemDto.problemId())).willReturn(false);

                // when & then
                assertThatThrownBy(() -> problemService.toggleFavorite(favoriteProblemDto))
                    .isInstanceOf(BaseException.class)
                    .extracting(error -> ((BaseException) error).getErrorCode())
                    .isEqualTo(ProblemErrorCode.NOT_FOUND_PROBLEM);
            })
        );
    }

    private static FavoriteProblemDto createFavoriteProblemDto(boolean isFavorite) {
        return FavoriteProblemDto.builder()
            .userId(1L)
            .problemId(1L)
            .isFavorite(isFavorite)
            .build();
    }
}
