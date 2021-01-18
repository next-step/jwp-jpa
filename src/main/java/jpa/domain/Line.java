package jpa.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "line")
public class Line extends BaseEntity {

    @Column(unique = true)
    private String name;
    private String color;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineStation> lineStations = new ArrayList<>();

    protected Line() {
    }

    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<LineStation> getLineStations() {
        return lineStations;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void addLineStation(Station station, Station previousStation, int distance) {
        Optional<LineStation> first = lineStations.stream()
            .filter(lineStation -> lineStation.getPreviousStation().equals(station))
            .findFirst();

        if (first.isPresent()) {
            LineStation lineStation = first.get();
            validateDistance(lineStation, distance);
            Station beforeStation = lineStation.getStation();
            removeLineStation(lineStation);
            this.lineStations.add(new LineStation(this, station, previousStation, distance));
            this.lineStations.add(new LineStation(this, beforeStation, station, distance - lineStation.getDistance()));
        } else {
            this.lineStations.add(new LineStation(this, station, previousStation, distance));
        }

    }

    private void removeLineStation(LineStation lineStation) {
        lineStation.remove();
        this.lineStations.remove(lineStation);
    }

    private void validateDistance(LineStation lineStation, Integer distance1) {
        if (!lineStation.greaterThanDistance(distance1)) {
            throw new IllegalArgumentException("입력하신 거리보다 이전역과 더 가까운 역이 존재합니다.");
        }
    }

    public List<Station> getStations() {
        Stream<Station> collect = this.lineStations.stream().map(LineStation::getStation);
        Stream<Station> collect1 = this.lineStations.stream().map(LineStation::getPreviousStation);
        return Stream.concat(collect, collect1).distinct().collect(Collectors.toList());
    }
}
