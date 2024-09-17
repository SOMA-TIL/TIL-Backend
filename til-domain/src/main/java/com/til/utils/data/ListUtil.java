package com.til.utils.data;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListUtil {

    public static <T> boolean isNullOrEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static <T> boolean isContain(List<T> list, T element) {
        return list.contains(element);
    }
}
