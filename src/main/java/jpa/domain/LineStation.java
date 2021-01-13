package jpa.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "line_station")
public class LineStation extends BaseEntity {

    @ManyToOne
    private Line line;
    @ManyToOne
    private Station station;
    @ManyToOne
    private Station previousStation;
    private Integer distance;

    protected LineStation() {
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
}
