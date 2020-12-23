package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Line extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String color;

    @OneToMany(mappedBy = "line")
    private final List<Station> stations = new ArrayList<>();

    protected Line() {
    }

    public Line(String name) {
        this.name = name;
    }

    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public void addStation(Station station) {
        stations.add(station);
        station.changeLine(this);
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

    public void changeColor(String color) {
        this.color = color;
    }
}
