package com.til.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.til.domain.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(String email);

	boolean existsByNickname(String nickname);
}
