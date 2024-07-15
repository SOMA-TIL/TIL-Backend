package com.til.application.user;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.til.domain.common.exception.BaseException;
import com.til.domain.user.dto.UserJoinDto;
import com.til.domain.user.dto.UserLoginDto;
import com.til.domain.user.repository.UserRepository;
import com.til.domain.user.validator.UserInfoValidator;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@InjectMocks
	private UserService userService;

	@Mock
	private UserInfoValidator userInfoValidator;

	@Mock
	private UserRepository userRepository;

	@Test
	void 회원가입시_이메일이_중복되면_예외를_던진다() {
		// given
		given(userRepository.existsByEmail(anyString())).willReturn(true);

		// when & then
		assertThatThrownBy(() -> userService.join(createUserJoinDto()))
			.isInstanceOf(BaseException.class);
	}

	@Test
	void 회원가입시_닉네임이_중복되면_예외를_던진다() {
		// given
		given(userRepository.existsByEmail(anyString())).willReturn(false);
		given(userRepository.existsByNickname(anyString())).willReturn(true);

		// when & then
		assertThatThrownBy(() -> userService.join(createUserJoinDto()))
			.isInstanceOf(BaseException.class);
	}

	@Test
	void 로그인시_회원으로_등록되지_않은_정보는_예외를_던진다() {
		// given
		given(userRepository.getByEmail(anyString())).willThrow(BaseException.class);

		// when & then
		assertThatThrownBy(() -> userService.login(createUserLoginDto()))
			.isInstanceOf(BaseException.class);
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
}
