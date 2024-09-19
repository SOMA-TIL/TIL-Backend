package com.til.common.utils.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BooleanUtil {

    public static boolean getOrDefault(Boolean value, boolean defaultValue) {
        return value != null ? value : defaultValue;
    }

    public static boolean getOrFalse(Boolean value) {
        return getOrDefault(value, false);
    }

    public static boolean getOrTrue(Boolean value) {
        return getOrDefault(value, true);
    }
}
