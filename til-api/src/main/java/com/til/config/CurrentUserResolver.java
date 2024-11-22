package com.til.config;

import static com.til.common.http.auth.enums.AuthConstants.AUTHORIZATION_HEADER;
import static com.til.common.http.auth.enums.AuthConstants.BEARER_TYPE;
import static java.util.Objects.isNull;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.til.common.http.auth.annotation.CurrentUser;
import com.til.domain.auth.dto.AuthUserInfoDto;
import com.til.domain.auth.provider.TokenProvider;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CurrentUserResolver implements HandlerMethodArgumentResolver {

    private final TokenProvider tokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (!parameter.hasParameterAnnotation(CurrentUser.class)) {
            return false;
        }

        return parameter.hasParameterAnnotation(CurrentUser.class)
            && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Long resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer,
        @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        CurrentUser currentUserAnnotation = parameter.getParameterAnnotation(CurrentUser.class);
        boolean required = currentUserAnnotation != null && currentUserAnnotation.required();

        return (!required && isNull(webRequest.getHeader(AUTHORIZATION_HEADER))) ? null
            : AuthUserInfoDto.of(tokenProvider.parseClaims(extractTokenFromRequest(webRequest))).id();
    }

    private String extractTokenFromRequest(NativeWebRequest webRequest) {
        return webRequest.getHeader(AUTHORIZATION_HEADER).substring(BEARER_TYPE.length() + 1);
    }
}
