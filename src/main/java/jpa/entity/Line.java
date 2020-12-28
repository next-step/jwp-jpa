package jpa.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Line extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 20, unique = true)
    private String name;

    @Column(name = "color", length = 20, nullable = false)
    private String color;

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL)
    private List<LineStation> lineStations = new ArrayList<>();

    @Builder
    public Line(String name, String color, List<Station> stations) {
        this.name = name;
        this.color = color;
        this.lineStations = Optional.ofNullable(stations).orElse(Collections.emptyList()).stream()
                .map(station -> new LineStation(this, station))
                .collect(Collectors.toList());
    }

    public void changeLine(String name) {
        this.name = name;
    }
}

