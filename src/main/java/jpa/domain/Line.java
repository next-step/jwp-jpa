package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Line extends BaseTimeEntity {
    @Column(unique = true, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private LineColor lineColor;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "line")
    private final List<StationLine> stationLines = new ArrayList<>();

    protected Line() {
    }

    public Line(String name) {
        this.name = name;
    }

    public Line(String name, LineColor lineColor) {
        this.name = name;
        this.lineColor = lineColor;
    }

    public void addStationLine(StationLine stationLine) {
        stationLines.add(stationLine);
    }

    public LineColor getColor() {
        return lineColor;
    }

    public String getName() {
        return name;
    }

    public List<StationLine> getStationLines() {
        return stationLines;
    }

    public void changeColor(LineColor lineColor) {
        this.lineColor = lineColor;
    }
}
