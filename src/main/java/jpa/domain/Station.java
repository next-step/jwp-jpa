package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "station")
public class Station extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "station")
    List<LineStation> lineStations = new ArrayList<>();

    @Column(unique = true)
    private String name;

    protected Station() {}

    public Station(String name) {
        this.name = name;
    }

    public void add(Line line) {
        lineStations.add(new LineStation(line, this, null, 0));
    }

    public void addLineStation(LineStation lineStation) {
        lineStations.add(lineStation);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<LineStation> getLineStations() {
        return lineStations;
    }
}
