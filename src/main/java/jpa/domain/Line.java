package jpa.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "line")
public class Line extends BaseEntity {


    public enum Color {
        RED, BLUE, GREEN;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_id")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "color")
    private Color color;

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineStation> lineStations = new ArrayList<>();

    public Line(final String name, final Color color) {
        this.name = name;
        this.color = color;
    }

    public Line changeName(final String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("옯바른 이름이 아닙니다.");
        }
        this.name = name;
        return this;
    }

    public List<Station> stations() {
        return lineStations.stream()
                .map(LineStation::getStation)
                .collect(Collectors.toList());
    }

    public void addLineStation(final Station preStation, final Station station, final Distance distance) {
        LineStation lineStation = new LineStation(this, preStation, station, distance);
        if (lineStations.contains(lineStation)) {
            throw new IllegalArgumentException("이미 등록된 지하철 역입니다.");
        }

        lineStations.add(lineStation);
        station.getLineStations().add(lineStation);
    }

    public void removeLineStation(final Station station) {
        LineStation targetStation = getLineStation(station);
        lineStations.remove(targetStation);
        station.getLineStations().remove(targetStation);
    }

    public LineStation getLineStation(final Station station) {
        return lineStations.stream()
                .filter(lineStation -> lineStation.isStation(station))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("등록된 지하철 역이 없습니다."));
    }
}

