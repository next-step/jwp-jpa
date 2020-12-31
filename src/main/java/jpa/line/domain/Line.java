package jpa.line.domain;

import jpa.common.domain.BaseEntity;
import jpa.location.domain.Location;
import jpa.station.domain.Station;

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

    public Line(LineType type) {
        validate(type);
        this.color = type.getColor();
        this.name = type.getName();
    }

    private void validate(LineType type) {
        if (type == null) {
            throw new IllegalArgumentException("노선의 정보는 입력되어야합니다.");
        }
    }

    public void addStation(Station station, Location location) {
        station.addLocation(location);
        stations.add(station);
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
