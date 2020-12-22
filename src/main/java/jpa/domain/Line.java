package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "line")
public class Line extends BaseEntity{
    @Column(unique = true)
    private String name;
    private String color;
    @ManyToMany
    private List<Station> stations = new ArrayList<>();

    protected Line() {}

    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public void add(Station station) {
        stations.add(station);
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<Station> getStations() {
        return stations;
    }
}
