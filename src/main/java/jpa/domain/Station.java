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
public class Station extends Base {

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "station", cascade = CascadeType.PERSIST)
    private List<LineStation> lineStations = new ArrayList<>();

    public void addLineStation(LineStation lineStation) {
        lineStations.add(lineStation);
    }

    public Station(String name) {
        this.name = name;
    }

    public static Station of(String name) {
        return new Station(name);
    }
}
