package com.til.application.grading;

import static com.til.domain.auth.enums.AuthConstants.AUTHORIZATION_HEADER;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.til.domain.grading.dto.GradingInputDataDto;
import com.til.domain.grading.dto.GradingResultDto;
import com.til.domain.grading.dto.InterviewGradingResultDto;
import com.til.domain.grading.enums.AnswerType;
import com.til.domain.grading.enums.GradingStatus;
import com.til.domain.grading.repository.GradingRepository;
import com.til.domain.interview.model.InterviewStatus;
import com.til.domain.interview.repository.InterviewProblemRepository;
import com.til.domain.interview.repository.InterviewRepository;
import com.til.domain.problem.repository.UserProblemRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class GradingService {

    @Value("${grading-service.api.url}")
    private String GRADING_API_URL;

    @Value("${grading-service.api.key}")
    private String GRADING_API_KEY;

    private final GradingRepository gradingRepository;
    private final UserProblemRepository userProblemRepository;
    private final InterviewRepository interviewRepository;
    private final InterviewProblemRepository interviewProblemRepository;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder().baseUrl(GRADING_API_URL).build();
    }

    @Async
    @Transactional
    public void makeGradingUserProblem(Long targetId) {
        GradingInputDataDto gradingInputData = prepareGradingInputFromUserProblem(targetId);
        log.info("Grading input data : {}", gradingInputData);

        CompletableFuture<GradingResultDto> gradingResultFuture = sendGradingRequest(gradingInputData);

        gradingResultFuture.thenAccept(result -> {
            gradingRepository.save(GradingResultDto.toEntity(AnswerType.PROBLEM, targetId, result));
            userProblemRepository.updateStatus(targetId, GradingStatus.COMPLETED);
        }).exceptionally(e -> {
            userProblemRepository.updateStatus(targetId, GradingStatus.ERROR);
            return null;
        });
    }

    @Async
    @Transactional
    public void makeGradingInterview(Long interviewId) {
        Map<Long, GradingInputDataDto> gradingInputDataList = prepareGradingInputFromInterview(interviewId);
        log.debug("Grading input data : {}", gradingInputDataList);

        List<CompletableFuture<Void>> futures = gradingInputDataList.entrySet().stream()
            .map(entry -> processInterviewGrading(entry.getKey(), entry.getValue()))
            .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).whenComplete((result, throwable) -> {
            if (throwable != null) {
                interviewRepository.updateInterviewStatus(interviewId, InterviewStatus.ERROR);
            } else {
                interviewRepository.updateInterviewStatus(interviewId, InterviewStatus.DONE);
            }
        });
    }

    private CompletableFuture<Void> processInterviewGrading(Long targetId, GradingInputDataDto gradingInputDataDto) {
        return sendGradingRequest(gradingInputDataDto).thenAccept(result -> {
            gradingRepository.save(GradingResultDto.toEntity(AnswerType.INTERVIEW, targetId, result));
            interviewProblemRepository.updateProblemGradingStatusById(targetId, GradingStatus.COMPLETED);
        }).exceptionally(e -> {
            interviewProblemRepository.updateProblemGradingStatusById(targetId, GradingStatus.ERROR);
            return null;
        });
    }

    public GradingResultDto getUserProblemGradingResult(Long userId, Long sourceId, Long submitId) {
        return gradingRepository.getResultFromUserProblem(userId, sourceId, submitId);
    }

    public InterviewGradingResultDto getInterviewGradingResult(Long userId, String interviewCode) {
        Long interviewId = interviewRepository.getIdByUserIdAndCode(userId, interviewCode);
        return gradingRepository.getResultFromInterview(userId, interviewId);
    }

    private GradingInputDataDto prepareGradingInputFromUserProblem(Long targetId) {
        return gradingRepository.getGradingInputDataFromUserProblem(targetId);
    }

    private Map<Long, GradingInputDataDto> prepareGradingInputFromInterview(Long interviewId) {
        return gradingRepository.getGradingInputDataFromInterview(interviewId);
    }

    private CompletableFuture<GradingResultDto> sendGradingRequest(GradingInputDataDto gradingInputDataDto) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return webClient.post()
                    .header(AUTHORIZATION_HEADER, GRADING_API_KEY)
                    .bodyValue(gradingInputDataDto)
                    .retrieve()
                    .bodyToMono(GradingResultDto.class)
                    .block();
            } catch (Exception e) {
                log.error("Grading request failed: {}", e.getMessage());
                throw new RuntimeException("Grading request failed", e);
            }
        });
    }
}
