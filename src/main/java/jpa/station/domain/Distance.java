package jpa.station.domain;

import javax.persistence.*;

@Embeddable
public class Distance {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_station_id", nullable = false)
    private Station previousStation;

    @Column(nullable = false)
    private Long previousDistance;

    protected Distance() {
    }

    public Distance(Station previousStation, long previousDistance) {
        validate(previousStation, previousDistance);
        this.previousStation = previousStation;
        this.previousDistance = previousDistance;
    }

    private void validate(Station previousStation, long previousDistance) {
        if (previousStation == null) {
            throw new IllegalArgumentException("이전 역 정보가 필요합니다.");
        }
        if (previousDistance < 0) {
            throw new IllegalArgumentException("이전 역과 거리 정보가 필요합니다.");
        }
    }

    public Station getPreviousStation() {
        return previousStation;
    }

    public long getPreviousDistance() {
        return previousDistance;
    }
}
