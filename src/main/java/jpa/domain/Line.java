package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Line extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    private LineColor color;

    @Column(unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "lines")
    private final List<Station> stations = new ArrayList<>();

    protected Line() {
    }

    public Line(LineColor color, String name) {
        this.color = color;
        this.name = name;
    }

    public void addStation(Station station) {
        station.addLine(this);
    }

    public void removeStation(Station station) {
        station.removeLine(this);
    }

    public void changeColor(LineColor color) {
        this.color = color;
    }

    public LineColor getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public List<Station> getStations() {
        return stations;
    }
}
