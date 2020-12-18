package jpa.domain.bridgeObject;

import jpa.domain.Line;
import jpa.domain.Station;
import jpa.utils.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class LineUseBridge extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "line", fetch = FetchType.LAZY)
    private List<LineStation> lineStations = new ArrayList<>();

    protected LineUseBridge() {
    }

    LineUseBridge(final Long id, final String color, final String name) {
        this.id = id;
        this.color = color;
        this.name = name;
    }

    public LineUseBridge(final String color, final String name) {
        this(null, color, name);
    }

    public void addLineStation(final LineStation lineStation) {
        this.lineStations.add(lineStation);
        lineStation.updateLine(this);
    }

    public Long getId() {
        return this.id;
    }

    public void updateLine(final LineUseBridge line) {
        this.createdDate = line.createdDate;
        this.modifiedDate = line.modifiedDate;
        this.name = line.name;
        this.color = line.color;
    }

    public String getColor() {
        return this.color;
    }

    public String getName() {
        return this.name;
    }

    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }

    public List<LineStation> getLineStations() {
        return this.lineStations;
    }

    public List<Station> getStations() {
        Set<Station> dupRemovedStations = lineStations.stream()
                .map(LineStation::getStations)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        return new ArrayList<>(dupRemovedStations).stream()
                .sorted(Comparator.comparingLong(Station::getId))
                .collect(Collectors.toList());
    }
}
