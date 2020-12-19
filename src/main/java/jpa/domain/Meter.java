package jpa.domain;

import java.util.Objects;

/**
 * @author : leesangbae
 * @project : jpa
 * @since : 2020-12-19
 */
public class Meter {

    public static Meter of(int meter) {
        return new Meter(meter);
    }

    private int meter;

    private Meter(int meter) {
        valid(meter);
        this.meter = meter;
    }

    private void valid(int meter) {
        if (meter < 0) {
            throw new IllegalArgumentException();
        }
    }

    public int getMeter() {
        return meter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Meter meter1 = (Meter) o;
        return meter == meter1.meter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(meter);
    }
}
