package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
public class Station extends Date {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "station", cascade = CascadeType.MERGE)
    private final List<LineStation> lineStations = new ArrayList<>();

    protected Station() {

    }

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public List<Line> getLines() {
        return lineStations.stream()
                .map(LineStation::getLine)
                .collect(Collectors.toList());
    }

    // 연관 관계 편의 메서드
    public void addLine(Line line) {
        lineStations.add(new LineStation(line, this));
        if (!line.getStations().contains(this)) {
            line.addStation(this);
        }
    }

    public void removeLine(Line line) {
        lineStations.removeIf(l -> l.getLine().getName().equals(line.getName()));
    }

}
