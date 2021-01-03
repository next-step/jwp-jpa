package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
public class Line extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String color;

    //@Column(nullable = false)
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "line", cascade = CascadeType.MERGE)
    private final List<LineStation> lineStations = new ArrayList<>();

    protected Line() {

    }

    public Line(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Station> getStations() {
        return lineStations.stream()
                .map(LineStation::getStation)
                .collect(Collectors.toList());
    }

    // 연관 관계 편의 메서드
    public void addStation(Station station) {
        lineStations.add(new LineStation(this, station));
        if (!station.getLines().contains(this)) {
            station.addLine(this);
        }
    }

    public void removeStation(Station station) {
        lineStations.removeIf(s -> s.getStation().getName().equals(station.getName()));
        if (station.getLines().contains(this)) {
            station.removeLine(this);
        }
    }

}
