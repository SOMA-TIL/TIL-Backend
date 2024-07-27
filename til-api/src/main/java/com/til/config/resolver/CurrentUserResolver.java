package com.til.config.resolver;

import java.util.Optional;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.til.application.user.UserService;
import com.til.common.annotation.CurrentUser;
import com.til.config.AppConfig;
import com.til.domain.common.enums.BaseErrorCode;
import com.til.domain.common.exception.BaseException;
import com.til.domain.user.dto.UserInfoDto;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CurrentUserResolver implements HandlerMethodArgumentResolver {

    private final AppConfig appConfig;
    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class)
            && parameter.getParameterType().equals(UserInfoDto.class);
    }

    @Override
    public UserInfoDto resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer,
        @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        if (!appConfig.isJwtFilterEnabled()) { // for test
            return userService.getUserInfo("til@gmail.com");
        }

        return Optional.of(SecurityContextHolder.getContext().getAuthentication())
            .filter(Authentication::isAuthenticated)
            .map(auth -> userService.getUserInfo(auth.getPrincipal().toString()))
            .orElseThrow(() -> new BaseException(BaseErrorCode.UNAUTHORIZED));
    }
}
