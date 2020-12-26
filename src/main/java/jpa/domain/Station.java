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

    @OneToMany(mappedBy = "station", orphanRemoval = true)
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
    public String toString() {
        return name;
    }
}
