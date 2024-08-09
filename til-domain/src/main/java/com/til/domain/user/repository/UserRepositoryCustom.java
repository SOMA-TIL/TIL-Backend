package com.til.domain.user.repository;

import com.til.domain.user.model.User;

public interface UserRepositoryCustom {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    User getByEmail(String email);

    String getPasswordById(Long id);

    void updatePassword(Long id, String password);

    Long getIdByEmail(String email);
}
