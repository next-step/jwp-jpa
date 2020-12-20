package jpa.domain;

import jpa.converter.DistanceConverter;
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
    @JoinColumn(name = "station_id")
    private Station station;

    @Convert(converter = DistanceConverter.class)
    private Distance distance;

    public LineStation(final Station station, final Line line, final Distance distance) {
        this.station = station;
        this.line = line;
        this.distance = distance;
    }

    public boolean isStation(final Station station) {
        return this.station.equals(station);
    }
}
