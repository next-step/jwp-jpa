package jpa.station.domain;

import jpa.common.domain.BaseEntity;
import jpa.line.domain.Line;
import jpa.location.domain.Location;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Station extends BaseEntity {

    @Column(unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "station_line",
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "line_id"))
    private final List<Line> lines = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "station", cascade = CascadeType.PERSIST)
    private final List<Location> locations = new ArrayList<>();

    protected Station() {
    }

    public Station(String name) {
        this.name = name;
    }

    public void addLine(Line line) {
        line.getStations().add(this);
        this.lines.add(line);
    }

    public void removeLine(Line line) {
        line.getStations().remove(this);
        this.lines.remove(line);
    }

    public void addLocation(Location location) {
        this.locations.add(location);
    }

    public void changeName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Line> getLines() {
        return lines;
    }

    public List<Location> getLocations() {
        return locations;
    }
}
