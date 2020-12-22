package jpa.domain;

import jpa.utils.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Line extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Distance> distances = new ArrayList<>();

    @ManyToMany
    @JoinColumn(name = "station_id")
    private List<Station> stations = new ArrayList<>();

    protected Line() {
    }

    Line(final Long id, final String color, final String name) {
        this.id = id;
        this.color = color;
        this.name = name;
    }

    public Line(final String color, final String name) {
        this(null, color, name);
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

    public void updateLine(final Line line) {
        this.color = line.color;
        this.name = line.name;
    }

    public void addDistance(final Distance distance) {
        if (!this.stations.contains(distance.getUpStation())) {
            this.addStation(distance.getUpStation());
        }
        if (!this.stations.contains(distance.getDownStation())) {
            this.addStation(distance.getDownStation());
        }
        this.distances.add(distance);
    }

    public void addStation(final Station station) {
        this.stations.add(station);
        station.getLines().add(this);
    }

    public List<Station> getStations() {
        return this.stations;
    }

    public List<Distance> getDistances() {
        return this.distances;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Line line = (Line) o;
        return Objects.equals(id, line.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Line{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
