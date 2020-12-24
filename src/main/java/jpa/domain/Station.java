package jpa.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Setter
@Getter
@Entity
public class Station extends BaseEntity {

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "station")
    private List<StationLine> stationLines = new ArrayList<>();

    public Station() {
    }

    public Station(String name) {
        this.name = name;
    }

    public void addStationLines(StationLine stationLine) {
        stationLines.add(stationLine);
        stationLine.setStation(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(name, station.name) &&
                Objects.equals(stationLines, station.stationLines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, stationLines);
    }

    @Override
    public String toString() {
        return name;
    }
}
