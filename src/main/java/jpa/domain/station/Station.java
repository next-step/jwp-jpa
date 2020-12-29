package jpa.domain.station;

import jpa.domain.common.BaseTimeEntity;
import jpa.domain.stationline.StationLine;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Station extends BaseTimeEntity {
    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "station")
    private final List<StationLine> stationLines = new ArrayList<>();

    protected Station() {
    }

    public Station(String name) {
        this.name = name;
    }

    public void addStationLine(StationLine stationLine) {
        stationLines.add(stationLine);
    }

    public String getName() {
        return name;
    }

    public List<StationLine> getStationLines() {
        return stationLines;
    }

    public void changeName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(this.getId(), station.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
