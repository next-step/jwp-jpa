package jpa.domain;

import javax.persistence.*;

@Entity
@Table
public class LineStation {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    @EmbeddedId
    private LineStationId id;

    @ManyToOne
    @MapsId("lineId")
    @JoinColumn(name = "line_id")
    private Line line;

    @ManyToOne
    @MapsId("stationId")
    @JoinColumn(name = "station_id")
    private Station station;

    @Embedded
    private Section section;

    public LineStation() {
    }

    public LineStation(Line line, Station station) {
        this.id = new LineStationId(line.getId(), station.getId());
        this.line = line;
        this.station = station;
    }

    public Line getLine() {
        return line;
    }

    public Station getStation() {
        return station;
    }

}
