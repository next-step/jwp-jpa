package jpa.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
public class Line extends BaseEntity {

    private String color;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "line", orphanRemoval = true)
    private List<StationLine> stationLines = new ArrayList<>();

    public Line() {
    }

    public Line(String name) {
        this.name = name;
    }

    public void addStationLines(StationLine stationLine) {
        stationLines.add(stationLine);
        stationLine.setLine(this);
    }

    @Override
    public String toString() {
        return name;
    }
}
