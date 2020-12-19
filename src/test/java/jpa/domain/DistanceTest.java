package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DistanceTest {

    @DisplayName("길이는 0이상의 숫자이다.")
    @ParameterizedTest
    @ValueSource(longs = {0, 10})
    void crate(long value) {
        // when
        Distance distance = Distance.ofMeter(value);

        // then
        assertThat(distance).isNotNull();
    }

    @DisplayName("길이는 음수 값일 수 없다.")
    @Test
    void createUnderRange() {
        // given
        long negative = -1L;

        // when / then
        assertThrows(IllegalArgumentException.class, () -> Distance.ofMeter(negative));
    }

    @DisplayName("길이가 같으면 동등성을 보장한다.")
    @ParameterizedTest
    @ValueSource(longs = {0, 10})
    void equals(long value) {
        // when
        Distance distance1 = Distance.ofMeter(value);
        Distance distance2 = Distance.ofMeter(value);

        // then
        assertThat(distance1).isEqualTo(distance2);
    }
}
