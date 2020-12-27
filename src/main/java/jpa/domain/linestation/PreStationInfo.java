package jpa.domain.linestation;

import jpa.domain.station.Station;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PreStationInfo {

    @Column(name = "distance")
    private Integer distance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pre_station_id")
    private Station preStation;

    private PreStationInfo(Station preStation, Integer distance) {
        this.distance = distance;
        this.preStation = preStation;
    }

    public static PreStationInfo of(Station preStation, Integer distance) {
        return new PreStationInfo(preStation, distance);
    }
}
