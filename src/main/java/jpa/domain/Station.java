package jpa.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Station extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany
    @JoinTable(name = "line_station")
    private List<Line> lines = new ArrayList<>();

    @OneToMany(mappedBy = "current")
    private List<StationDistance> stationDistances = new ArrayList<>();

    public Station(String name) {
        this.name = name;
    }

    public Station(String name, Line... lines) {
        validate(lines);
        List<Line> lineList = Arrays.asList(lines);

        this.name = name;
        this.lines.addAll(lineList);
        addLines(lineList);
    }

    public void changeName(String name) {
        this.name = name;
    }

    private void validate(Line[] lines) {
        if (lines == null || lines.length == 0) {
            throw new IllegalArgumentException("최소 하나의 노선정보를 입력하세요");
        }
    }

    private void addLines(List<Line> lineList) {
        lineList.forEach(line -> line.addByStation(this));
    }

    public void addByStationDistance(StationDistance stationDistance) {
        this.stationDistances.add(stationDistance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Station station = (Station) o;
        return Objects.equals(id, station.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
