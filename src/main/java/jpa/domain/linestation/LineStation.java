package jpa.domain.linestation;

import jpa.domain.line.Line;
import jpa.domain.station.Station;

import javax.persistence.*;

@Entity
public class LineStation {

    @EmbeddedId
    LineStationId id;

    @ManyToOne
    @MapsId("line_id")
    @JoinColumn(name = "line_id")
    Line line;

    @ManyToOne
    @MapsId("station_id")
    @JoinColumn(name = "station_id")
    Station station;

    protected LineStation() {
    }

    public LineStation(Line line, Station station) {
        this.line = line;
        this.station = station;
    }
}


