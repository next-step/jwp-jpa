package jpa.domain;

import jpa.utils.BaseEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class LineStation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id")
    private Line line;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    protected  LineStation() {
    }

    LineStation(final Long id, final Line line, final Station station) {
        this.id = id;
        this.line = line;
        this.station = station;
    }

    LineStation(final Line line, final Station station) {
        this(null, line, station);
    }

    void updateLine(final Line line) {
        this.line = line;
        line.addLineStation(this);
    }

    void updateStation(final Station station) {
        this.station = station;
        station.addLineStation(this);
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
