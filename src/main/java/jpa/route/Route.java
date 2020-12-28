package jpa.route;

import jpa.base.BaseEntity;
import jpa.line.Line;
import jpa.station.Station;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"line_id", "station_id", "next_station_id"}))
public class Route extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "line_id", insertable = false, updatable = false)
    private Line line;

    @ManyToOne
    @JoinColumn(name = "station_id", insertable = false, updatable = false)
    private Station station;

    @ManyToOne
    @JoinColumn(name = "next_station_id", insertable = false, updatable = false)
    private Station nextStation;

    private int distance;

    protected Route() {}

    public Route(Line line, Station station, Station nextStation, int distance) {
        this.line = line;
        this.station = station;
        this.nextStation = nextStation;
        this.distance = distance;
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

    public Station getNextStation() {
        return nextStation;
    }

    public int getDistance() {
        return distance;
    }
}
