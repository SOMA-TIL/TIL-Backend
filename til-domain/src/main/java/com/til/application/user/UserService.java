package com.til.application.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.domain.common.exception.BaseException;
import com.til.domain.user.dto.UserJoinDto;
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
		userInfoValidator.validateJoinInfo(userJoinDto.nickname(), userJoinDto.password());

		if (userRepository.existsByEmail(userJoinDto.email())) {
			throw new BaseException(UserErrorCode.ALREADY_EXISTS_EMAIL);
		}
		checkNickname(userJoinDto.nickname());

		String encodePassword = passwordEncoder.encode(userJoinDto.password());
		User user = userJoinDto.toEntity();
		user.setPassword(encodePassword);

		userRepository.save(user);
	}

	public void checkNickname(String nickname) {
		if (userRepository.existsByNickname(nickname)) {
			throw new BaseException(UserErrorCode.ALREADY_EXISTS_NICKNAME);
		}
	}
}
