package jpa.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(of = {"line", "station"}, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "line_station")
public class LineStation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_station_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;

    @ManyToOne
    @JoinColumn(name = "pre_station_id")
    private Station preStation;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @Embedded
    private Distance distance;

    public LineStation(final Line line, final Station preStation, final Station station, final Distance distance) {
        this.line = line;
        this.preStation = preStation;
        this.station = station;
        this.distance = distance;
    }

    public boolean isStation(final Station station) {
        return this.station.equals(station);
    }
}
