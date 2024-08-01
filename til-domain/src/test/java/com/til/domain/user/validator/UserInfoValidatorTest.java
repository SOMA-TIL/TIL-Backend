package com.til.domain.user.validator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.til.domain.common.exception.InvalidDtoException;

class UserInfoValidatorTest {

    private final UserInfoValidator userInfoValidator = new UserInfoValidator();

    private static Stream<Arguments> validNicknameProvider() {
        return Stream.of(
            Arguments.of("닉네임 정보가 2자인 경우", "ab"),
            Arguments.of("닉네임 정보가 12자인 경우", "123456789012"),
            Arguments.of("닉네임 정보가 한글로만 구성되어 있는 경우", "한글닉네임"),
            Arguments.of("닉네임 정보가 영문으로만 구성되어 있는 경우", "Nickname"),
            Arguments.of("닉네임 정보가 숫자로만 구성되어 있는 경우", "1234"),
            Arguments.of("닉네임 정보가 한글, 영문, 숫자로 구성되어 있는 경우", "한글en12")
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("validNicknameProvider")
    void 닉네임_정보가_유효하면_예외를_던지지_않는다(String description, String nickname) {
        assertDoesNotThrow(() -> userInfoValidator.validateNickname(nickname));
    }

    private static Stream<Arguments> invalidNicknameProvider() {
        return Stream.of(
            Arguments.of("닉네임 정보가 1자 이하인 경우", "a", InvalidDtoException.class),
            Arguments.of("닉네임 정보가 13자 이상인 경우", "12345678901234", InvalidDtoException.class),
            Arguments.of("닉네임 정보가 특수문자를 포함한 경우", "nickname!", InvalidDtoException.class)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidNicknameProvider")
    void 닉네임_정보가_유효하지_않으면_예외를_던진다(String description, String nickname,
        Class<? extends Exception> expectedException) {
        assertThatThrownBy(() -> userInfoValidator.validateNickname(nickname))
            .isInstanceOf(expectedException);
    }

    private static Stream<Arguments> validPasswordProvider() {
        return Stream.of(
            Arguments.of("비밀번호 정보가 영문, 숫자 조합으로 8자 이상 20자 이하인 경우", "Password1234"),
            Arguments.of("비밀번호 정보가 8자인 경우", "Password1"),
            Arguments.of("비밀번호 정보가 20자인 경우", "Password123456789012")
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("validPasswordProvider")
    void 비밀번호_정보가_유효하면_예외를_던지지_않는다(String description, String password) {
        assertDoesNotThrow(() -> userInfoValidator.validatePassword(password));
    }

    private static Stream<Arguments> invalidPasswordProvider() {
        return Stream.of(
            Arguments.of("비밀번호 정보가 7자인 경우", "Passwo1", InvalidDtoException.class),
            Arguments.of("비밀번호 정보가 21자인 경우", "Password1234567890123", InvalidDtoException.class),
            Arguments.of("비밀번호 정보가 영문만 포함된 경우", "Password", InvalidDtoException.class),
            Arguments.of("비밀번호 정보가 숫자만 포함된 경우", "12345678", InvalidDtoException.class),
            Arguments.of("비밀번호 정보가 특수문자를 포함한 경우", "Password!", InvalidDtoException.class)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidPasswordProvider")
    void 비밀번호_정보가_유효하지_않으면_예외를_던진다(String description, String password,
        Class<? extends Exception> expectedException) {
        assertThatThrownBy(() -> userInfoValidator.validatePassword(password))
            .isInstanceOf(expectedException);
    }
}
