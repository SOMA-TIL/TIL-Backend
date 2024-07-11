package com.til.config.security;

public class PathPermission {
	public static String[] getPublicPath() {
		return new String[] {"/user/**"};
	}

	public static String[] getAdminPath() {
		return new String[] {};
	}
}
