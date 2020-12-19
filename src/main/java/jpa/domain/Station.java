package jpa.domain;

import jpa.utils.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Station extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "upStation", fetch = FetchType.LAZY)
    private List<LineStation> lineStations = new ArrayList<>();

    protected Station() {
    }

    Station(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Station(final String name) {
        this(null, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addLineStation(final LineStation lineStation) {
        lineStation.updateUpStation(this);
        this.lineStations.add(lineStation);
    }

    public void updateStation(final Station station) {
        this.name = station.name;
    }

    List<LineStation> getLineStations() {
        return this.lineStations;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Station station = (Station) o;
        return Objects.equals(id, station.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
