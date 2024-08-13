package com.til.config.resolver;

import static com.til.domain.auth.enums.AuthConstants.AUTHORIZATION_HEADER;
import static java.util.Objects.isNull;

import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.til.application.auth.AuthService;
import com.til.common.annotation.CurrentUser;
import com.til.config.AppConfig;
import com.til.domain.auth.dto.AuthUserInfoDto;
import com.til.domain.user.model.Role;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CurrentUserResolver implements HandlerMethodArgumentResolver {

    private final AppConfig appConfig;
    private final AuthService authService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (!parameter.hasParameterAnnotation(CurrentUser.class)) {
            return false;
        }

        return parameter.hasParameterAnnotation(CurrentUser.class)
            && parameter.getParameterType().equals(AuthUserInfoDto.class);
    }

    @Override
    public AuthUserInfoDto resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer,
        @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        CurrentUser currentUserAnnotation = parameter.getParameterAnnotation(CurrentUser.class);
        boolean required = currentUserAnnotation != null && currentUserAnnotation.required();

        if (!appConfig.isJwtFilterEnabled()) { // for test
            return AuthUserInfoDto.builder().id(6L).role(Role.USER).build();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
            return (AuthUserInfoDto) authentication.getPrincipal();
        }

        return (!required && isNull(webRequest.getHeader(AUTHORIZATION_HEADER))) ? null
            : authService.getUserInfoFromToken(extractTokenFromRequest(webRequest));
    }

    private String extractTokenFromRequest(NativeWebRequest webRequest) {
        return webRequest.getHeader(AUTHORIZATION_HEADER);
    }
}
