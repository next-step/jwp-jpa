package jpa.domain.manyToMany;

import jpa.domain.Station;
import jpa.utils.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ManyToManyLine extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    private String name;

    @ManyToMany
    @JoinTable(name = "MANY")
    private List<ManyToManyStation> stations = new ArrayList<>();

    protected ManyToManyLine() {
    }

    ManyToManyLine(final Long id, final String color, final String name) {
        this.id = id;
        this.color = color;
        this.name = name;
    }

    public void addStation(final ManyToManyStation station) {
        station.addLine(this);
        this.stations.add(station);
    }

    public void updateLine(final ManyToManyLine line) {
        this.color = line.color;
        this.name = line.name;
    }

    public ManyToManyLine(final String color, final String name) {
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

    public List<ManyToManyStation> getStations() {
        return stations;
    }
}
