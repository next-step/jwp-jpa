package jpa.route;

import jpa.base.BaseEntity;
import jpa.line.Line;
import jpa.station.Station;

import javax.persistence.*;

@Entity
public class Route extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Line line;

    @ManyToOne
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Station station;

    @ManyToOne
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Station nextStation;

    private int distance;

    private boolean upward;

    protected Route() {}

    public Route(Line line, Station station, Station nextStation, int distance, boolean upward) {
        this.line = line;
        this.station = station;
        this.nextStation = nextStation;
        this.distance = distance;
        this.upward = upward;
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

    public boolean isUpward() {
        return upward;
    }
}
