package jpa.domain.bridgeObject;

import jpa.domain.Line;
import jpa.domain.Station;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
public class LineStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id")
    private LineUseBridge line;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "up_station_id")
    private Station upStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "down_station_id")
    private Station downStation;

    private Long distance;

    protected LineStation() {
    }

    LineStation(final Long id, final LineUseBridge line, final Station upStation, final Station downStation, final Long distance) {
        this.id = id;
        this.line = line;
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    public LineStation(final Station upStation, final Station downStation, final Long distance) {
        this(null, null, upStation, downStation, distance);
    }

    public void updateLine(final LineUseBridge lineUseBridge) {
        this.line = lineUseBridge;
        line.getLineStations().add(this);
    }

    public Long getId() {
        return id;
    }

    public LineUseBridge getLine() {
        return line;
    }

    public Station getUpStation() {
        return upStation;
    }

    public Station getDownStation() {
        return downStation;
    }

    public List<Station> getStations() {
        return Arrays.asList(upStation, downStation);
    }

    public Long getDistance() {
        return distance;
    }
}
