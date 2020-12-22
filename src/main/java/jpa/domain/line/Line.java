package jpa.domain.line;

import jpa.domain.BaseDateTimeEntity;
import jpa.domain.station.Station;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Line extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String color;

    @Column(unique = true)
    private String name;

    @ManyToMany
    @JoinTable(name = "line_station",
            joinColumns = @JoinColumn(name = "line_id"),
            inverseJoinColumns = @JoinColumn(name = "station_id"))
    private List<Station> stations = new ArrayList<>();

    protected Line() {}

    public Line(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void addStation(Station station) {
        stations.add(station);
        station.addLine(this);
    }
}
