package com.til.filter;

import static com.til.domain.auth.enums.AuthConstants.AUTHORIZATION_HEADER;
import static com.til.domain.auth.enums.AuthConstants.BEARER_TYPE;
import static com.til.utils.data.ListUtil.isContain;
import static com.til.utils.data.ListUtil.isNullOrEmpty;

import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.til.common.http.errorhandling.ErrorResponse;
import com.til.common.http.response.enums.BaseErrorCode;
import com.til.domain.auth.dto.AuthUserInfoDto;
import com.til.domain.auth.provider.TokenProvider;
import com.til.domain.user.model.Role;
import com.til.filter.AuthorizationHeaderFilter.Config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<Config> {

    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;

    public AuthorizationHeaderFilter(TokenProvider tokenProvider) {
        super(Config.class);
        this.tokenProvider = tokenProvider;
        this.objectMapper = new ObjectMapper();
    }

    @Getter
    @Setter
    public static class Config {

        private boolean required;

        private List<Role> requiredRole;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String token = getTokenFromHeader(request);

            if (!isValidateToken(token)) {
                return handleUnAuthorized(exchange);
            }

            AuthUserInfoDto info = AuthUserInfoDto.of(tokenProvider.parseClaims(token));
            if (!isContain(config.getRequiredRole(), info.role())) {
                return handleUnAuthorized(exchange);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> handleUnAuthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(BaseErrorCode.UNAUTHORIZED.getStatus());
        try {
            String errorString = objectMapper.writeValueAsString(ErrorResponse.of(BaseErrorCode.UNAUTHORIZED));
            return response.writeWith(Mono.just(response.bufferFactory().wrap(errorString.getBytes())));
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }
    }

    private String getTokenFromHeader(ServerHttpRequest request) {
        List<String> authHeaders = request.getHeaders().get(AUTHORIZATION_HEADER);
        return isNullOrEmpty(authHeaders) ? null : authHeaders.get(0).substring(BEARER_TYPE.length()).trim();
    }

    private boolean isValidateToken(String token) {
        try {
            tokenProvider.validateToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
