package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Station extends BaseTimeEntity {
    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "station")
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
}
