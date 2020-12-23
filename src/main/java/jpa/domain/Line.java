package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "line")
public class Line extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany
    @JoinColumn(name = "line")
    private List<LineStation> lineStations = new ArrayList<>();

    private String color;

    protected Line() {}

    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public void add(Station station) {
        lineStations.add(new LineStation(this, station, null, 0));
    }

    public void addLineStation(LineStation lineStation) {
        lineStations.add(lineStation);
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<LineStation> getLineStations() {
        return lineStations;
    }

    public Long getId() {
        return id;
    }
}
