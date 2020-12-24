package jpa.domain;

import javax.persistence.*;

@Entity
public class LineStation extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id")
    private Line line;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "old_station_id")
    private Station oldStation;

    private int distance;

    public LineStation(Line line, Station station, Station oldStation, int distance) {
        this.line = line;
        this.station = station;
        this.oldStation = oldStation;
        this.distance = distance;
        line.addLineStation(this);
        station.addLineStation(this);
    }

    public Long getId() {
        return id;
    }

    public Line getLine() {
        return line;
    }

    public Station getStation() {
        return station;
    }

    public Station getOldStation() {
        return oldStation;
    }

    public int getDistance() {
        return distance;
    }
}
