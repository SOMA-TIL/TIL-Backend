package com.til.common.http.resolver;

import static java.util.Objects.isNull;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.til.common.http.auth.annotation.CurrentUser;
import com.til.common.http.auth.enums.AuthConstants;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CurrentUserResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (!parameter.hasParameterAnnotation(CurrentUser.class)) {
            return false;
        }

        return parameter.hasParameterAnnotation(CurrentUser.class)
            && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Long resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        CurrentUser currentUserAnnotation = parameter.getParameterAnnotation(CurrentUser.class);
        boolean required = currentUserAnnotation != null && currentUserAnnotation.required();

        return (!required && isNull(webRequest.getHeader(AuthConstants.X_USER_ID))) ? null
            : Long.parseLong(extractTokenFromRequest(webRequest));
    }

    private String extractTokenFromRequest(NativeWebRequest webRequest) {
        return webRequest.getHeader(AuthConstants.X_USER_ID);
    }
}
