package com.til.config.resolver;

import static com.til.domain.auth.enums.AuthConstants.AUTHORIZATION_HEADER;

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
import com.til.common.annotation.CurrentAdmin;
import com.til.domain.auth.dto.AuthUserInfoDto;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CurrentAdminResolver implements HandlerMethodArgumentResolver {

    private final AuthService authService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentAdmin.class)
            && parameter.getParameterType().equals(AuthUserInfoDto.class);
    }

    @Override
    public AuthUserInfoDto resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer,
        @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
            return (AuthUserInfoDto) authentication.getPrincipal();
        }

        return authService.getUserInfoFromToken(extractTokenFromRequest(webRequest));
    }

    private String extractTokenFromRequest(NativeWebRequest webRequest) {
        return webRequest.getHeader(AUTHORIZATION_HEADER);
    }
}
