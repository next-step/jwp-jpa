package jpa.domain.favorite;

import jpa.domain.common.BaseTimeEntity;
import jpa.domain.station.Station;

import javax.persistence.*;

@Entity
public class Favorite extends BaseTimeEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "depart_station_id")
    private Station departureStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrive_station_id")
    private Station arrivalStation;

    protected Favorite() {
    }

    public Favorite(Station departureStation, Station arrivalStation) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
    }

    public Station getDepartureStation() {
        return departureStation;
    }

    public Station getArrivalStation() {
        return arrivalStation;
    }
}
