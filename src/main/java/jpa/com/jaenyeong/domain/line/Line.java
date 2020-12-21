package jpa.com.jaenyeong.domain.line;

import jpa.com.jaenyeong.domain.BaseEntity;
import jpa.com.jaenyeong.domain.mapping.LineStation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "LINE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Line extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "line", orphanRemoval = true)
    private List<LineStation> stations = new ArrayList<>();

    public Line(final String name) {
        this.name = name;
    }

    public Line(final String name, final String color) {
        this.name = name;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public void changeLineColor(final String color) {
        this.color = color;
    }

    public void add(final LineStation lineStation) {
        stations.add(lineStation);
    }

    public int haveStationsSize() {
        return stations.size();
    }

    public List<String> getStationsName() {
        return stations.stream()
            .map(LineStation::getStationName)
            .collect(Collectors.toList());
    }
}
