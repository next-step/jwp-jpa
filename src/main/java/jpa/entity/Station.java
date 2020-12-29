package jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "station", indexes = {
        @Index(name = "UK_station_idx", columnList = "name", unique = true)
})
public class Station extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
    private List<LineStation> lineStations = new ArrayList<>();

    @Builder
    public Station(String name, List<Line> lines) {
        this.name = name;
        this.lineStations = Optional.ofNullable(lines).orElse(Collections.emptyList()).stream()
                .map(line -> new LineStation(line, this))
                .collect(Collectors.toList());
    }

    public void changeStation(String name) {
        this.name = name;
    }
}


