package jpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AttributeOverride(name = "id", column = @Column(name = "station_id"))
public class Station extends Base {

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "stations", cascade = CascadeType.PERSIST)
    private Set<Line> lines = new HashSet<>();

    public void addLine(Line line) {
        lines.add(line);
        line.getStations().add(this);
    }

    public Station(String name) {
        this.name = name;
    }

    public static Station of(String name) {
        return new Station(name);
    }
}
