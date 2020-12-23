package jpa.domain.station;

import jpa.domain.base.BaseEntity;
import jpa.domain.line.Line;
import jpa.domain.linestation.LineStation;
import lombok.*;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "station", indexes = {
        @Index(name = "index_station", columnList = "name", unique = true)
})
public class Station extends BaseEntity {

    @Column(name = "name", unique=true)
    private String name;

    @OneToMany(mappedBy = "station", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineStation> lineStations = new ArrayList<>();

    @Builder
    private Station(String name, List<Line> lines) {
        this.name = name;
        this.lineStations = Optional.ofNullable(lines).orElse(Collections.emptyList()).stream()
                .map(line -> LineStation.of(line, this))
                .collect(Collectors.toList());
    }

    public Station updateName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(name, station.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

