package com.til.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.til.domain.common.exception.BaseException;
import com.til.domain.user.enums.UserErrorCode;
import com.til.domain.user.model.User;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    default User getById(Long id) {
        return findById(id).orElseThrow(() -> new BaseException(UserErrorCode.NOT_FOUND_USER));
    }
}
