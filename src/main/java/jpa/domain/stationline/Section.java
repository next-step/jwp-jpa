package jpa.domain.stationline;

import jpa.domain.station.Station;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class Section {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_station_id")
    private Station previousStation;

    private int distance;

    protected Section() {
    }

    private Section(Station previousStation, int distance) {
        this.previousStation = previousStation;
        this.distance = distance;
    }

    public static Section of(Station previousStation, int distance) {
        return new Section(previousStation, distance);
    }

    public Station getPreviousStation() {
        return previousStation;
    }

    public int getDistance() {
        return distance;
    }
}
