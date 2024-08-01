package com.til.application.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.domain.auth.dto.AuthUserInfoDto;
import com.til.domain.common.exception.BaseException;
import com.til.domain.user.dto.UserInfoDto;
import com.til.domain.user.dto.UserJoinDto;
import com.til.domain.user.dto.UserLoginDto;
import com.til.domain.user.enums.UserErrorCode;
import com.til.domain.user.model.User;
import com.til.domain.user.repository.UserRepository;
import com.til.domain.user.validator.UserInfoValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserInfoValidator userInfoValidator;

    @Transactional
    public void join(UserJoinDto userJoinDto) {
        checkJoinInfo(userJoinDto);

        User user = userJoinDto.toEntity();
        user.setPassword(passwordEncoder.encode(userJoinDto.password()));

        userRepository.save(user);
    }

    public AuthUserInfoDto login(UserLoginDto userLoginDto) {
        User user = userRepository.getByEmail(userLoginDto.email());
        if (!passwordEncoder.matches(userLoginDto.password(), user.getPassword())) {
            throw new BaseException(UserErrorCode.FAILED_LOGIN);
        }

        return AuthUserInfoDto.of(user.getEmail(), user.getNickname(), user.getRole());
    }

    public UserInfoDto getUserInfo(String email) {
        User user = userRepository.getByEmail(email);
        return UserInfoDto.of(user);
    }

    public void checkEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BaseException(UserErrorCode.ALREADY_EXISTS_EMAIL);
        }
    }

    public void checkNickname(String nickname) {
        userInfoValidator.validateNickname(nickname);
        if (userRepository.existsByNickname(nickname)) {
            throw new BaseException(UserErrorCode.ALREADY_EXISTS_NICKNAME);
        }
    }

    private void checkJoinInfo(UserJoinDto userJoinDto) {
        userInfoValidator.validatePassword(userJoinDto.password());
        checkEmail(userJoinDto.email());
        checkNickname(userJoinDto.nickname());
    }
}
