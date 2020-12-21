package jpa.domain.manyToMany;

import jpa.utils.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ManyToManyLine extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String color;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "station_id")
    private List<ManyToManyStation> stations = new ArrayList<>();

    protected ManyToManyLine() {
    }

    ManyToManyLine(final Long id, final String name, final String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public ManyToManyLine(final String name, final String color) {
        this(null, name, color);
    }

    public void addStation(final ManyToManyStation station) {
        this.stations.add(station);
        station.getLines().add(this);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<ManyToManyStation> getStations() {
        return stations;
    }
}
