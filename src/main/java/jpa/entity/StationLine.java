package jpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StationLine extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "line_id")
    private Line line;

    private int distance;

    public StationLine(Station station, Line line, int distance) {
        this.station = station;
        this.line = line;
        this.distance = distance;
    }

    public void changeStationLine(Station station, Line line, int distance) {
        if(Objects.nonNull(this.station)
                && Objects.nonNull(this.line)
                && Objects.nonNull(this.station)) {
            this.station.getStationLines().remove(this);
            this.line.getStationLines().remove(this);
        }
        this.station = station;
        this.line = line;
        this.distance = distance;
        station.getStationLines().add(this);
        line.getStationLines().add(this);
    }
}
