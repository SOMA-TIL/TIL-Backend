package com.til.application.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.common.exception.BaseException;
import com.til.common.http.security.PasswordManager;
import com.til.domain.auth.dto.AuthUserInfoDto;
import com.til.domain.user.dto.UserLoginDto;
import com.til.domain.user.enums.UserErrorCode;
import com.til.domain.user.model.User;
import com.til.domain.user.repository.admin.AdminUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;

    private final PasswordManager passwordManager;

    public AuthUserInfoDto login(UserLoginDto userLoginDto) {
        User user = adminUserRepository.getByEmail(userLoginDto.email());
        if (passwordManager.passwordDoesNotMatch(userLoginDto.password(), user.getPassword())) {
            throw new BaseException(UserErrorCode.FAILED_LOGIN);
        }

        return AuthUserInfoDto.of(user.getId(), user.getNickname(), user.getRole());
    }
}
