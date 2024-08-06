package com.til.config.security;

import java.util.stream.Stream;

import org.springframework.util.AntPathMatcher;

public class PathPermission {

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    public static String[] getPublicPath() {
        return new String[]{
            "/user/join", "/user/login", "/user/check-nickname/**",
            "/problem", "/problem/{id}", "/category"};
    }

    public static String[] getAdminPath() {
        return new String[]{};
    }

    public static boolean isPublicPath(String path) {
        return Stream.of(PathPermission.getPublicPath())
            .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }
}
