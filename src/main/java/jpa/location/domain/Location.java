package jpa.location.domain;

import jpa.common.domain.BaseEntity;
import jpa.line.domain.Line;
import jpa.station.domain.Distance;
import jpa.station.domain.Station;

import javax.persistence.*;

@Entity
public class Location extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id", nullable = false)
    private Line line;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    @Embedded
    private Distance distance;

    protected Location() {
    }

    public Location(Line line, Station station, Distance distance) {
        validate(line, station, distance);
        this.line = line;
        this.station = station;
        this.distance = distance;
    }

    private void validate(Line line, Station station, Distance distance) {
        if (line == null) {
            throw new IllegalArgumentException("노선은 비어있을 수 없습니다.");
        }
        if (station == null) {
            throw new IllegalArgumentException("역은 비어있을 수 없습니다.");
        }
        if (distance == null) {
            throw new IllegalArgumentException("이전 역과의 정보는 가지고 있어야합니다.");
        }
    }

    public Line getLine() {
        return line;
    }

    public Station getStation() {
        return station;
    }

    public Distance getDistance() {
        return distance;
    }
}
