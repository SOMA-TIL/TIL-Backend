package com.til.config.db;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisManager {
	private final RedisTemplate<String, String> redisTemplate;

	public void setData(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
	}

	public void setData(String key, String value, long duration) {
		Duration expireDuration = Duration.ofSeconds(duration);
		redisTemplate.opsForValue().set(key, value, expireDuration);
	}

	public String getData(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public void deleteData(String key) {
		redisTemplate.delete(key);
	}

	public boolean existData(String key) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}
}
