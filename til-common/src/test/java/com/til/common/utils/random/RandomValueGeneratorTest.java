package com.til.common.utils.random;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RandomValueGeneratorTest {

    @Test
    void 랜덤ID_사이즈가_0_이하면_예외를_던진다() {
        // given
        int size = 0;

        // when & then
        assertThatThrownBy(() -> RandomValueGenerator.generateRandomString(size)).isInstanceOf(
            IllegalArgumentException.class);
    }

    @Test
    void 랜덤ID를_정상적으로_반환한다() {
        // given
        int size = 11;

        // when & then
        assertThat(RandomValueGenerator.generateRandomString(size).length()).isEqualTo(size);
    }

}
