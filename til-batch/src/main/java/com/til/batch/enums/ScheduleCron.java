package com.til.batch.enums;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScheduleCron {

    public static final String EVERY_SECOND = "0/1 * * * * *";
    public static final String EVERY_10_SECONDS = "0/10 * * * * *";
    public static final String EVERY_MINUTE = "0 * * * * ?";
    public static final String EVERY_HOUR = "0 0 * * * *";
}
