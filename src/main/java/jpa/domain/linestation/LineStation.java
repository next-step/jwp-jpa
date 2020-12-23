package jpa.domain.linestation;

import jpa.domain.line.Line;
import jpa.domain.station.Station;

import javax.persistence.*;

@Entity
public class LineStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Line line;

    @ManyToOne(cascade = CascadeType.ALL)
    private Station preStation;

    @ManyToOne(cascade = CascadeType.ALL)
    private Station station;

    int distance;

    protected LineStation() {
    }

    public LineStation(Station preStation, Station station, int distance) {
        changeStation(station);
        this.preStation = preStation;
        this.distance = distance;
    }

    public void changeStation(Station station) {
        this.station = station;
    }

    public void changeLine(Line line) {
        this.line = line;
        line.addLineStation(this);
    }

    public Long getId() {
        return id;
    }

    public Line getLine() {
        return line;
    }

    public Station getPreStation() {
        return preStation;
    }

    public Station getStation() {
        return station;
    }

    public int getDistance() {
        return distance;
    }
}
