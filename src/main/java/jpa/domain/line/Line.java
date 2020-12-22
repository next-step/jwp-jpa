package jpa.domain.line;

import jpa.domain.BaseEntity;
import jpa.domain.station.Station;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Line extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    @Column(unique = true)
    private String name;

    @ManyToMany
    @JoinTable(name = "line_station",
            joinColumns = @JoinColumn(name = "line_id"),
            inverseJoinColumns =  @JoinColumn(name = "station_id")
    )
    private final List<Station> stations = new ArrayList<>();

    protected Line() {
    }

    public Line(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public Long getId() {
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
    }
}
