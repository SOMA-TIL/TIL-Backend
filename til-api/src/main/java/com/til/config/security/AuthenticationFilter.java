package com.til.config.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.til.common.response.ApiStatus;
import com.til.domain.auth.provider.TokenProvider;
import com.til.domain.common.enums.BaseErrorCode;
import com.til.domain.user.model.Role;

import io.jsonwebtoken.Claims;
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
	private final TokenProvider tokenProvider;
	private final AntPathMatcher pathMatcher = new AntPathMatcher();
	private final ObjectMapper objectMapper = new ObjectMapper();

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BEARER_TYPE = "Bearer";

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getRequestURI();
		return Stream.of(PathPermission.getPublicPath())
			.anyMatch(pattern -> pathMatcher.match(pattern, path));
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
		@NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
		log.debug("[REQUEST_INFO] ({}) URI={}", request.getMethod(), request.getRequestURI());

		if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = resolveToken(request);
		if (token == null) {
			handleException(response);
			return;
		}

		try {
			tokenProvider.validateToken(token);
		} catch (Exception e) {
			handleException(response);
			return;
		}

		Authentication auth = createAuthentication(tokenProvider.parseClaims(token));
		SecurityContextHolder.getContext().setAuthentication(auth);
		filterChain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER))
			.filter(token -> token.startsWith(BEARER_TYPE))
			.map(token -> token.substring(BEARER_TYPE.length() + 1))
			.orElse(null);
	}

	private UsernamePasswordAuthenticationToken createAuthentication(Claims claims) {
		String email = claims.getSubject();
		Role role = Role.valueOf(claims.get("role").toString());
		return new UsernamePasswordAuthenticationToken(email, null, getAuthorities(role));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(() -> "ROLE_" + role.name());
		return collectors;
	}

	private void handleException(HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(ApiStatus.of(BaseErrorCode.UNAUTHORIZED)));
	}
}
