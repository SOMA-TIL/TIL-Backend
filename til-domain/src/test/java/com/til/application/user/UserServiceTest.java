package com.til.application.user;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.til.domain.common.exception.BaseException;
import com.til.domain.user.dto.UserJoinDto;
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
	@DisplayName("중복되는 이메일은 회원가입할 수 없다.")
	void joinFailWhenEmailDuplicated() {
		// given
		given(userRepository.existsByEmail(anyString())).willReturn(true);

		UserJoinDto userJoinDto = createUserJoinDto();

		// when
		Throwable throwable = catchThrowable(() -> userService.join(userJoinDto));

		// then
		assertThat(throwable).isInstanceOf(BaseException.class);
	}

	@Test
	@DisplayName("중복되는 닉네임은 회원가입할 수 없다.")
	void joinFailWhenNicknameDuplicated() {
		// given
		given(userRepository.existsByEmail(anyString())).willReturn(false);
		given(userRepository.existsByNickname(anyString())).willReturn(true);

		UserJoinDto userJoinDto = createUserJoinDto();

		// when
		Throwable throwable = catchThrowable(() -> userService.join(userJoinDto));

		// then
		assertThat(throwable).isInstanceOf(BaseException.class);
	}

	private UserJoinDto createUserJoinDto() {
		return UserJoinDto.builder()
			.email("sun@til.com")
			.password("pw@2024!")
			.nickname("선인장24")
			.build();
	}
}
