package jpa.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Setter
@Getter
@Entity
public class StationLine extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;

    public StationLine() {
    }

    public StationLine(Station station, Line line) {
        this.station = station;
        this.line = line;
        station.addStationLines(this);
        line.addStationLines(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationLine that = (StationLine) o;
        return Objects.equals(station, that.station) &&
                Objects.equals(line, that.line);
    }

    @Override
    public int hashCode() {
        return Objects.hash(station, line);
    }

    @Override
    public String toString() {
        return "StationLine{" +
                "station=" + station +
                ", line=" + line +
                '}';
    }
}
