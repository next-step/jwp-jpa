package jpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AttributeOverride(name = "id", column = @Column(name = "line_id"))
public class Line extends Base {

    @Column(unique = true)
    private String name;

    private String color;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "line_station",
            joinColumns = @JoinColumn(name = "line_id"),
            inverseJoinColumns = @JoinColumn(name = "station_id"))
    private Set<Station> stations = new HashSet<>();

    public void addStation(Station station) {
        stations.add(station);
        station.getLines().add(this);
    }

    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public static Line of(String name, String color) {
        return new Line(name, color);
    }
}
