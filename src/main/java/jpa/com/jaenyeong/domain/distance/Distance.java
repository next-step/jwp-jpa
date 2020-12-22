package jpa.com.jaenyeong.domain.distance;

import jpa.com.jaenyeong.domain.station.Station;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Distance {
    private static final int EMPTY = 0;
    private long meter;

    @JoinColumn(name = "departure_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Station previous;

    public Distance(final Station previous, final long meter) {
        if (previous == null || meter <= EMPTY) {
            throw new IllegalArgumentException("not valid parameters");
        }
        this.previous = previous;
        this.meter = meter;
    }

    public long getDistanceForMeter() {
        return meter;
    }

    public String getPreviousStationName() {
        return previous.getName();
    }
}
