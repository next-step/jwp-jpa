package jpa.domain.station;

import jpa.domain.BaseEntity;
import jpa.domain.linestation.LineStation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Station extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "station")
    private final List<LineStation> lineStations = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<LineStation> getLineStations() {
        return lineStations;
    }

    protected Station() {
    }

    public Station(String name) {
        this.name = name;
    }

    public void addLineStations(LineStation line) {
        lineStations.add(line);
        line.changeStation(this);
    }
}
