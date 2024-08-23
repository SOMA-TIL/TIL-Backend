package com.til.utils.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {

    public static boolean hasText(String str) {
        return str != null && !str.trim().isEmpty();
    }

}
