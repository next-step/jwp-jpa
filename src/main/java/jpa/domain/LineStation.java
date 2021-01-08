package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "line_station")
public class LineStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;

    public void setStation(Station station) {
        this.station = station;
    }

    public void changeLine(Line line) {
        this.line = line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Station getStation() {
        return station;
    }

    public Line getLine() {
        return line;
    }
}
