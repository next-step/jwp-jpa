package jpa.domain;

import javax.persistence.*;

@Entity
public class StationLine extends BaseTimeEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id")
    private Line line;

    @Embedded
    private Section section;

    protected StationLine() {
    }

    public StationLine(Station station, Line line, Section section) {
        validate(station, line);
        this.station = station;
        this.line = line;
        this.section = section;

        station.addStationLine(this);
        line.addStationLine(this);
    }

    private void validate(Station station, Line line) {
        if (station == null || line == null) {
            throw new NullPointerException("Station 또는 Line이 null입니다.");
        }
    }

    public Station getStation() {
        return station;
    }

    public Line getLine() {
        return line;
    }

    public Section getSection() {
        return section;
    }
}
