package jpa.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class StationLine extends BaseTimeEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id")
    private Line line;

    protected StationLine() {
    }

    public StationLine(Station station, Line line) {
        this.station = station;
        this.line = line;
        station.addStationLine(this);
        line.addStationLine(this);
    }

    public Station getStation() {
        return station;
    }

    public Line getLine() {
        return line;
    }
}
