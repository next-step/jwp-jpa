package jpa.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "line_station")
public class LineStation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Line line;
    @ManyToOne(fetch = FetchType.LAZY)
    private Station station;
    @ManyToOne(fetch = FetchType.LAZY)
    private Station previousStation;
    private Integer distance;

    protected LineStation() {
    }

    public LineStation(Line line, Station station, Station previousStation, Integer distance) {
        this.line = line;
        this.station = station;
        this.previousStation = previousStation;
        this.distance = distance;
    }

    public Line getLine() {
        return line;
    }

    public Station getStation() {
        return station;
    }

    public Station getPreviousStation() {
        return previousStation;
    }

    public Integer getDistance() {
        return distance;
    }


    public boolean greaterThanDistance(Integer distance) {
        return this.distance > distance;
    }

    public void remove() {
        this.station.remove(this);
        this.previousStation.remove(this);
    }

}
