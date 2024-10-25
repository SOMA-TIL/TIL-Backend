package com.til.common.utils.random;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RandomValueGenerator {

    private static final int BYTE_BIT_LENGTH = 8;
    private static final int B64_BIT_LENGTH = 6;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Encoder base64Encoder = Base64.getEncoder();

    public static String generateRandomString(int size) {

        if (size <= 0) {
            throw new IllegalArgumentException("랜덤 ID의 크기는 최소 1 이상이어야 합니다.");
        }

        int requiredMinimumBitLength = ((size - 1) * B64_BIT_LENGTH) + 1;

        int byteLength = requiredMinimumBitLength / BYTE_BIT_LENGTH;
        if (byteLength * 8 < requiredMinimumBitLength) {
            byteLength++;
        }

        byte[] bytes = new byte[byteLength];
        secureRandom.nextBytes(bytes);

        return base64Encoder.encodeToString(bytes)
            .replaceAll("=", "")
            .replaceAll("[+]", "-")
            .replaceAll("/", "_")
            .substring(0, size);
    }

}
