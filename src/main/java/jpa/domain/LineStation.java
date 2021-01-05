package jpa.domain;

import javax.persistence.*;

@Entity
@Table
public class LineStation {

    @EmbeddedId
    private LineStationId id;

    @ManyToOne
    @MapsId("lineId")
    @JoinColumn
    private Line line;

    @ManyToOne
    @MapsId("stationId")
    @JoinColumn(name = "station_id")
    private Station station;

    @Embedded
    private Section section;

    public LineStation() {
    }

    public LineStation(Line line, Station station) {
        this.id = new LineStationId(line.getId(), station.getId());
        this.line = line;
        this.station = station;
        this.section = new Section(station, station, 0);
    }

    public LineStation(Line line, Section section) {
        this(line, section.getEndStation());
        this.section = section;
    }

    public Line getLine() {
        return line;
    }

    public Station getStation() {
        return station;
    }

    public Section getSection() {
        return section;
    }
}
