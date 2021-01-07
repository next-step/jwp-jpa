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

    @OneToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;

    @OneToMany(mappedBy = "lineStation")
    private List<Line> lines = new ArrayList<>();

    public LineStation(Station station) {
        this.station = station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public void setLines(Line line) {
        this.lines.add(line);
    }

    public Station getStation() {
        return this.station;
    }

    public List<Line> getLines() {
        return lines;
    }
}
