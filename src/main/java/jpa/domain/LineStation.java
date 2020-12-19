package jpa.domain;

import jpa.utils.BaseEntity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
public class LineStation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "line_id")
    private Line line;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "up_station_id")
    private Station upStation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "down_station_id")
    private Station downStation;

    private int distance;

    protected  LineStation() {
    }

    LineStation(final Long id, final Line line, final Station upStation, final Station downStation, final int distance) {
        this.id = id;
        this.line = line;
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    LineStation(final Line line, final Station upStation, final Station downStation, final int distance) {
        this(null, line, upStation, downStation, distance);
    }

    void updateLine(final Line line) {
        this.line = line;
        line.getLineStations().add(this);
    }

    void updateUpStation(final Station station) {
        this.upStation = station;
        station.getLineStations().add(this);
    }

    List<Station> getStations() {
        return Arrays.asList(upStation, downStation);
    }

    Line getLine() {
        return this.line;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final LineStation that = (LineStation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
