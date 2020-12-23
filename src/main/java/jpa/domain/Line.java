package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Line extends BaseTimeEntity {
    @Column(unique = true, nullable = false)
    private String name;

    private String color;

    @OneToMany(mappedBy = "line")
    private final List<StationLine> stationLines = new ArrayList<>();

    protected Line() {
    }

    public Line(String name) {
        this.name = name;
    }

    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public void addStationLine(StationLine stationLine) {
        stationLines.add(stationLine);
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public List<StationLine> getStationLines() {
        return stationLines;
    }

    public void changeColor(String color) {
        this.color = color;
    }
}
