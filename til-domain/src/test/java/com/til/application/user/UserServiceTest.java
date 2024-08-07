package com.til.application.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.til.domain.common.exception.BaseException;
import com.til.domain.user.dto.UserJoinDto;
import com.til.domain.user.dto.UserLoginDto;
import com.til.domain.user.dto.UserPasswordDto;
import com.til.domain.user.enums.UserErrorCode;
import com.til.domain.user.repository.UserRepository;
import com.til.domain.user.validator.UserInfoValidator;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserInfoValidator userInfoValidator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Test
    void 회원가입시_이메일이_중복되면_예외를_던진다() {
        // given
        given(userRepository.existsByEmail(anyString())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> userService.join(createUserJoinDto()))
            .isInstanceOf(BaseException.class)
            .extracting(error -> ((BaseException) error).getErrorCode())
            .isEqualTo(UserErrorCode.ALREADY_EXISTS_EMAIL);
    }

    @Test
    void 회원가입시_닉네임이_중복되면_예외를_던진다() {
        // given
        given(userRepository.existsByEmail(anyString())).willReturn(false);
        given(userRepository.existsByNickname(anyString())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> userService.join(createUserJoinDto()))
            .isInstanceOf(BaseException.class)
            .extracting(error -> ((BaseException) error).getErrorCode())
            .isEqualTo(UserErrorCode.ALREADY_EXISTS_NICKNAME);
    }

    @Test
    void 로그인시_회원으로_등록되지_않은_정보는_예외를_던진다() {
        // given
        given(userRepository.getByEmail(anyString())).willThrow(new BaseException(UserErrorCode.NOT_FOUND_USER));

        // when & then
        assertThatThrownBy(() -> userService.login(createUserLoginDto()))
            .isInstanceOf(BaseException.class)
            .extracting(error -> ((BaseException) error).getErrorCode())
            .isEqualTo(UserErrorCode.NOT_FOUND_USER);
    }

    @Test
    void 비밀번호_변경시_기존_비밀번호_정보가_유효하지_않으면_예외를_던진다() {
        // given
        given(userRepository.getPasswordById(anyLong())).willReturn("otherPassword");
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        // when & then
        assertThatThrownBy(() -> userService.changePassword(1L, createUserPasswordDto("pass1234", "new12345")))
            .isInstanceOf(BaseException.class)
            .extracting(error -> ((BaseException) error).getErrorCode())
            .isEqualTo(UserErrorCode.NOT_MATCH_PASSWORD);
    }

    @Test
    void 비밀번호_변경시_새로운_비밀번호가_기존_비밀번호와_같으면_예외를_던진다() {
        // given
        given(userRepository.getPasswordById(anyLong())).willReturn("pass1234");
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> userService.changePassword(1L, createUserPasswordDto("pass1234", "pass1234")))
            .isInstanceOf(BaseException.class)
            .extracting(error -> ((BaseException) error).getErrorCode())
            .isEqualTo(UserErrorCode.SAME_PASSWORD);
    }

    private UserJoinDto createUserJoinDto() {
        return UserJoinDto.builder()
            .email("test@til.com")
            .password("soma2024")
            .nickname("선인장24")
            .build();
    }

    private UserLoginDto createUserLoginDto() {
        return UserLoginDto.builder()
            .email("test@til.com")
            .password("soma2024")
            .build();
    }

    private UserPasswordDto createUserPasswordDto(String password, String newPassword) {
        return UserPasswordDto.builder()
            .password(password)
            .newPassword(newPassword)
            .build();
    }
}
