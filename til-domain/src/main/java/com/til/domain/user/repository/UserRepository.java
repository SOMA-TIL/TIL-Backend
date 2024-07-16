package com.til.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.til.domain.common.exception.BaseException;
import com.til.domain.user.enums.UserErrorCode;
import com.til.domain.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<User> findByEmail(String email);

    default User getByEmail(String email) {
        return findByEmail(email).orElseThrow(() -> new BaseException(UserErrorCode.NOT_FOUND_USER));
    }
}
