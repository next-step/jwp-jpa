package jpa.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@Entity
public class StationLine extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "line_id")
    private Line line;

    @Embedded
    private PreviousStation previousStation;

    public StationLine() {
    }

    public StationLine(PreviousStation previousStation, Station station, Line line) {
        this.previousStation = previousStation;
        this.station = station;
        this.line = line;
        station.addStationLines(this);
        line.addStationLines(this);
    }

    @Override
    public String toString() {
        return "StationLine{" +
                "station=" + station +
                ", line=" + line +
                ", previousStation=" + previousStation +
                '}';
    }
}
