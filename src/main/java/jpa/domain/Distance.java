package jpa.domain;

import java.util.Objects;

public class Distance {

    private static final long MIN_VALUE = 0;

    private final long meter;

    private Distance(final Long meter) {
        if (meter < MIN_VALUE) {
            throw new IllegalArgumentException("거리 값은 음수가 될 수 없습니다.");
        }
        this.meter = meter;
    }

    public static Distance ofMeter(final long meter) {
        return new Distance(meter);
    }

    public Long toMeter() {
        return meter;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Distance)) return false;
        final Distance distance = (Distance) o;
        return meter == distance.meter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(meter);
    }
}
