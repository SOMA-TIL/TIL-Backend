package com.til.application.interview;

import static com.til.common.http.auth.enums.AuthConstants.AUTHORIZATION_HEADER;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.til.common.config.properties.LlmApiProperties;
import com.til.common.exception.BaseException;
import com.til.common.utils.random.RandomValueGenerator;
import com.til.domain.category.dto.InterviewCategoryDto;
import com.til.domain.category.repository.InterviewCategoryRepository;
import com.til.domain.category.repository.ProblemCategoryRepository;
import com.til.domain.grading.enums.GradingStatus;
import com.til.domain.interview.dto.CreatingProblemInputDataDto;
import com.til.domain.interview.dto.CreatingProblemResultData;
import com.til.domain.interview.dto.CreatingProblemResultDto;
import com.til.domain.interview.dto.InterviewCodeDto;
import com.til.domain.interview.dto.InterviewCreateDto;
import com.til.domain.interview.dto.InterviewInfoDto;
import com.til.domain.interview.dto.InterviewProblemQuestionDto;
import com.til.domain.interview.dto.InterviewProblemSnapshotDto;
import com.til.domain.interview.dto.InterviewSolveDto;
import com.til.domain.interview.enums.InterviewErrorCode;
import com.til.domain.interview.model.Interview;
import com.til.domain.interview.model.InterviewProblem;
import com.til.domain.interview.model.InterviewProblemStatus;
import com.til.domain.interview.model.InterviewStatus;
import com.til.domain.interview.model.InterviewType;
import com.til.domain.interview.repository.InterviewProblemRepository;
import com.til.domain.interview.repository.InterviewRepository;
import com.til.domain.problem.repository.ProblemRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class InterviewService {

    private static final int RANDOM_ID_SIZE = 11;

    private final LlmApiProperties llmApiProps;

    private final InterviewRepository interviewRepository;
    private final InterviewCategoryRepository interviewCategoryRepository;
    private final InterviewProblemRepository interviewProblemRepository;
    private final ProblemRepository problemRepository;

    private final ProblemCategoryRepository problemCategoryRepository;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder().baseUrl(llmApiProps.getUrl()).build();
    }

    @Transactional
    public InterviewCodeDto createInterview(InterviewCreateDto interviewCreateDto) {
        checkCreatingOrProcessingInterviewByUserId(interviewCreateDto.userId());

        String code = createRandomId();

        Interview interview = interviewCreateDto.toEntity(code);
        interviewRepository.save(interview);

        // -- cs 질문 기반 면접 생성
        if (interviewCreateDto.interviewType().equals(InterviewType.NORMAL)) {
            createInterviewCategory(interview.getId(), interviewCreateDto.categoryIdList());
            createInterviewProblem(interviewCreateDto.categoryIdList(), interview.getQuestionSize(), interview.getId());
            interviewRepository.updateInterviewStatus(interview.getId(), InterviewStatus.PROCESSING);
        }
        // -- portfolio 질문 기반 면접 생성
        else if (interviewCreateDto.interviewType().equals(InterviewType.PORTFOLIO)) {
            createInterviewProblemWithPortfolio(interview.getId(), interviewCreateDto.questionSize(), interviewCreateDto
                .portfolio());
        }

        return InterviewCodeDto.of(interview);
    }

    public InterviewInfoDto getProcessingInterviewInfo(Long userId, String code) {
        Interview interview = interviewRepository.getProcessingInterview(userId, code);

        List<Long> categoryIdList = (interview.getType().equals(InterviewType.NORMAL)) ? interviewCategoryRepository
            .getCategoryIdListByInterviewId(interview.getId()) : new ArrayList<>();

        List<InterviewProblemQuestionDto> problemList = interviewProblemRepository
            .getInterviewProblemQuestionByInterviewId(interview.getId());

        return InterviewInfoDto.of(interview.getType(), interview.getCreatedDate(), categoryIdList, problemList);
    }

    public InterviewStatus getInterviewStatus(Long userId, String code) {
        return interviewRepository.getInterviewStatus(userId, code);
    }

    @Transactional
    public void solveInterviewProblem(InterviewSolveDto interviewSolveDto) {
        Interview interview = interviewRepository.getProcessingInterview(interviewSolveDto.userId(), interviewSolveDto
            .code());

        checkInterviewProblemSolvable(interview.getId(), interviewSolveDto.sequence());
        checkInterviewProblemSequence(interview.getId(), interviewSolveDto.sequence());
        interviewProblemRepository.solveInterviewProblem(interview.getId(), interviewSolveDto.sequence(),
            interviewSolveDto.answer());

    }

    @Transactional
    public Long submitInterview(Long userId, String code) {
        Interview interview = interviewRepository.getProcessingInterview(userId, code);

        checkInterviewProblemAllSolved(interview.getId());

        interviewProblemRepository.updateProblemGradingStatusByInterviewId(interview.getId(), GradingStatus.PENDING);

        interviewRepository.updateInterviewStatus(interview.getId(), InterviewStatus.PENDING);

        return interview.getId();
    }

    private void checkDuplicateCode(String code) {
        if (interviewRepository.existsByCode(code)) {
            throw new BaseException(InterviewErrorCode.FAIL_CREATE_INTERVIEW);
        }
    }

    private void checkProcessingInterviewByUserId(Long userId) {
        if (interviewRepository.existsByUserIdAndStatus(userId, InterviewStatus.PROCESSING)) {
            throw new BaseException(InterviewErrorCode.ALREADY_PROCESSING_INTERVIEW);
        }
    }

    private void checkCreatingOrProcessingInterviewByUserId(Long userId) {
        if (interviewRepository.existsByUserIdAndCreatingOrProcessingStatus(userId)) {
            throw new BaseException(InterviewErrorCode.ALREADY_PROCESSING_INTERVIEW);
        }
    }

    private void checkInterviewProblemSolvable(Long interviewId, Integer sequence) {
        if (!interviewProblemRepository.existsBySolvable(interviewId, sequence, InterviewProblemStatus.UNSOLVED)) {
            throw new BaseException(InterviewErrorCode.NOT_FOUND_INTERVIEW_PROBLEM);
        }
    }

    private void checkInterviewProblemSequence(Long interviewId, Integer sequence) {
        if (interviewProblemRepository.existsBySequenceConsistency(interviewId, sequence,
            InterviewProblemStatus.UNSOLVED)) {
            throw new BaseException(InterviewErrorCode.INTERVIEW_SEQUENCE_INCONSISTENCY);
        }
    }

    private void checkInterviewProblemAllSolved(Long interviewId) {
        if (interviewProblemRepository.existsByInterviewIdAndStatus(interviewId, InterviewProblemStatus.UNSOLVED)) {
            throw new BaseException(InterviewErrorCode.FAIL_SUBMIT_INTERVIEW);
        }
    }

    private void createInterviewCategory(Long interviewId, List<Long> categoryIdList) {
        categoryIdList.forEach((categoryId) -> interviewCategoryRepository.save(InterviewCategoryDto.of(interviewId,
            categoryId).toEntity()));
    }

    private void createInterviewProblem(List<Long> categoryIdList, int questionSize, Long interviewId) {
        List<InterviewProblem> interviewProblemList = new ArrayList<>();

        int categorySize = categoryIdList.size();
        int assignedProblemSize = questionSize / categorySize;
        int remainProblemSize = assignedProblemSize + (questionSize % categorySize);
        int assignedSequence = 1;

        for (int i = 0; i < categorySize; i++) {
            Long categoryId = categoryIdList.get(i);
            int currentQuestionSize = (i + 1 != categorySize) ? assignedProblemSize : remainProblemSize; // 마지막 카테고리인 경우 문제 사이즈 변동

            List<InterviewProblemSnapshotDto> problemList = problemRepository.getInterviewProblemSnapshotList(
                categoryId, currentQuestionSize);

            for (InterviewProblemSnapshotDto interviewProblemSnapshotDto : problemList) {
                interviewProblemList.add(
                    InterviewProblem.createUnsolvedInterviewProblem(interviewProblemSnapshotDto.question(),
                        interviewProblemSnapshotDto.gradingCriteria(), assignedSequence++, interviewId,
                        interviewProblemSnapshotDto.problemId())
                );
            }
        }

        // todo: Bulk Insert 리팩토링
        interviewProblemRepository.saveAll(interviewProblemList);
    }

    @Async
    @Transactional
    public void createInterviewProblemWithPortfolio(Long interviewId, int questionSize, String portfolio) {
        CreatingProblemInputDataDto creatingProblemInputDataDto = CreatingProblemInputDataDto.of(questionSize,
            portfolio);

        CompletableFuture.supplyAsync(() -> sendingCreatingProblemRequest(creatingProblemInputDataDto))
            .thenAccept(creatingProblemResultData -> {
                List<CreatingProblemResultDto> creatingProblemResultDtoList = creatingProblemResultData.questionList();
                saveInterviewProblemWithPortfolio(interviewId, creatingProblemResultDtoList);

                interviewRepository.updateInterviewStatus(interviewId, InterviewStatus.PROCESSING);
            }).exceptionally(e -> {
                interviewRepository.updateInterviewStatus(interviewId, InterviewStatus.ERROR);
                return null;
            });
    }

    private void saveInterviewProblemWithPortfolio(Long interviewId,
        List<CreatingProblemResultDto> creatingProblemResultDtoList) {
        List<InterviewProblem> interviewProblemList = new ArrayList<>();

        for (int i = 0; i < creatingProblemResultDtoList.size(); i++) {
            CreatingProblemResultDto creatingProblemResultDto = creatingProblemResultDtoList.get(i);

            InterviewProblem unsolvedInterviewProblemWithPortfolio = InterviewProblem
                .createUnsolvedInterviewProblemWithPortfolio(
                    creatingProblemResultDto.question(),
                    creatingProblemResultDto.gradingCriteria(),
                    i + 1,
                    interviewId
                );

            interviewProblemList.add(unsolvedInterviewProblemWithPortfolio);
        }

        interviewProblemRepository.saveAll(interviewProblemList);
    }

    private CreatingProblemResultData sendingCreatingProblemRequest(
        CreatingProblemInputDataDto creatingProblemInputDataDto) {

        try {
            return webClient.post()
                .uri("/creating-problem")
                .header(AUTHORIZATION_HEADER, llmApiProps.getKey())
                .bodyValue(creatingProblemInputDataDto)
                .retrieve()
                .bodyToMono(CreatingProblemResultData.class)
                .block();
        } catch (Exception e) {
            log.error("Creating problem request failed: {}", e.getMessage());
            throw new RuntimeException("Creating problem request failed", e);
        }
    }

    private String createRandomId() {
        String code = RandomValueGenerator.generateRandomString(RANDOM_ID_SIZE);
        checkDuplicateCode(code);
        return code;
    }

}
