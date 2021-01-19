package jpa.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "station")
public class Station extends BaseEntity {

    @Column(unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<LineStation> lineStations = new ArrayList<>();

    protected Station() {
    }

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void changeName(String name) {
        this.name = name;
    }

    public void addLineStation(Line line, Station previousStation, int distance) {
        this.lineStations.add(new LineStation(line, this, previousStation, distance));
    }

    public void remove(LineStation lineStation) {
        this.lineStations.remove(lineStation);
    }

    public List<Line> getLines() {
        return this.lineStations.stream().map(LineStation::getLine).collect(Collectors.toList());
    }


}
