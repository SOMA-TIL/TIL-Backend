package com.til.domain.user.validator;

import org.springframework.stereotype.Component;

import com.til.domain.common.exception.InvalidDtoException;

@Component
public class UserInfoValidator {

    private static final String NICKNAME_REGEX = "^[가-힣A-Za-z0-9]{2,12}$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$";

    public void validateJoinInfo(String nickname, String password) {
        validateNickname(nickname);
        validatePassword(password);
    }

    private void validateNickname(String nickname) {
        if (!nickname.matches(NICKNAME_REGEX)) {
            throw new InvalidDtoException("닉네임은 한글, 영문, 숫자로 2자 이상 12자 이하로 입력해주세요.");
        }
    }

    private void validatePassword(String password) {
        if (!password.matches(PASSWORD_REGEX)) {
            throw new InvalidDtoException("비밀번호는 영문, 숫자 조합으로 8자 이상 20자 이하로 입력해주세요.");
        }
    }
}
