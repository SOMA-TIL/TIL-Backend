package com.til.application.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.til.common.exception.BaseException;
import com.til.common.http.security.PasswordManager;
import com.til.domain.user.dto.UserLoginDto;
import com.til.domain.user.enums.UserErrorCode;
import com.til.domain.user.model.User;
import com.til.domain.user.repository.admin.AdminUserRepository;

@ExtendWith(MockitoExtension.class)
class AdminUserServiceTest {

    @InjectMocks
    private AdminUserService adminUserService;

    @Mock
    private PasswordManager passwordManager;

    @Mock
    private AdminUserRepository adminUserRepository;

    @Test
    void 관리자가_아닌_사람이_로그인시_예외를_던진다() {
        given(adminUserRepository.getByEmail(anyString())).willThrow(new BaseException(UserErrorCode.NOT_FOUND_USER));

        assertThatThrownBy(() -> adminUserService.login(createUserLoginDto()))
            .isInstanceOf(BaseException.class)
            .extracting(error -> ((BaseException) error).getErrorCode())
            .isEqualTo(UserErrorCode.NOT_FOUND_USER);
    }

    @Test
    void 비밀번호가_일치하지_않으면_예외를_던진다() {
        given(adminUserRepository.getByEmail(anyString())).willReturn(User.builder().password("soma2024").build());
        given(passwordManager.passwordDoesNotMatch(anyString(), anyString())).willReturn(true);

        assertThatThrownBy(() -> adminUserService.login(createUserLoginDto()))
            .isInstanceOf(BaseException.class)
            .extracting(error -> ((BaseException) error).getErrorCode())
            .isEqualTo(UserErrorCode.FAILED_LOGIN);
    }

    private UserLoginDto createUserLoginDto() {
        return UserLoginDto.builder()
            .email("test@til.com")
            .password("soma2024")
            .build();
    }
}
