package jpa.domain.linestation;

import jpa.domain.base.BaseTimeEntity;
import jpa.domain.line.Line;
import jpa.domain.station.Station;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class LineStation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne
    @JoinColumn(name = "pre_station_id")
    private Station preSation;

    private int dinstance;

    protected LineStation() {
    }

    public LineStation(Line line, Station station) {
        validate(line, station);
        this.line = line;
        this.station = station;
    }

    public LineStation(Line line, Station station, Station preSation, int dinstance) {
        this(line, station);
        this.preSation = preSation;
        this.dinstance = dinstance;
    }

    private void validate(Line line, Station station) {
        if (line == null || station == null) {
            throw new IllegalArgumentException("필수값 누락입니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineStation that = (LineStation) o;
        return Objects.equals(line, that.line) &&
                Objects.equals(station, that.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, station);
    }
}


