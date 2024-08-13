package com.til.config.security;

import static com.til.domain.auth.enums.AuthConstants.AUTHORIZATION_HEADER;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.til.application.auth.AuthService;
import com.til.config.AppConfig;
import com.til.config.errorhandling.ErrorResponse;
import com.til.domain.auth.dto.AuthUserInfoDto;
import com.til.domain.common.enums.BaseErrorCode;
import com.til.domain.user.model.Role;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {

    private final AppConfig appConfig;
    private final AuthService authService;
    private final ObjectMapper objectMapper;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !appConfig.isJwtFilterEnabled() || PathPermission.isPublicPath(request.getRequestURI());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
        @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.debug("[REQUEST_INFO] ({}) URI={}", request.getMethod(), request.getRequestURI());

        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractTokenFromRequest(request);

        if (token == null || !authService.isValidateToken(token)) {
            handleException(response);
            return;
        }

        AuthUserInfoDto authUserInfoDto = authService.getUserInfoFromToken(token);
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(authUserInfoDto, null, getAuthorities(authUserInfoDto.role())));
        filterChain.doFilter(request, response);
    }

    public String extractTokenFromRequest(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> "ROLE_" + role.name());
        return collectors;
    }

    private void handleException(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(ErrorResponse.of(BaseErrorCode.UNAUTHORIZED)));
    }
}
