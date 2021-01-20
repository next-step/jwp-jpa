package jpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class LineStation extends Base {

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Line line;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Station station;

    private int distance;

    @ManyToOne(cascade = CascadeType.PERSIST,  fetch = FetchType.LAZY)
    private Station preStation;

    public LineStation(Line line, Station station, int distance, Station preStation) {
        this.line = line;
        this.station = station;
        this.distance = distance;
        this.preStation = preStation;
    }

    public static LineStation of(Line line, Station station, int distance, Station preStation) {
        return new LineStation(line, station, distance, preStation);
    }
}
