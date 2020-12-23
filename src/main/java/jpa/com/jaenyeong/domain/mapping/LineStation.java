package jpa.com.jaenyeong.domain.mapping;

import jpa.com.jaenyeong.domain.BaseEntity;
import jpa.com.jaenyeong.domain.distance.Distance;
import jpa.com.jaenyeong.domain.line.Line;
import jpa.com.jaenyeong.domain.station.Station;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "LINE_STATION_MAPPING")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LineStation extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn(name = "line_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Line line;

    @JoinColumn(name = "station_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Station station;

    @Embedded
    private Distance distance;

    public LineStation(final Line line, final Station station) {
        this(line, station, new Distance(null, 0));
    }

    public LineStation(final Line line, final Station station, final Distance distance) {
        if (line == null || station == null) {
            throw new IllegalArgumentException("invalid parameter");
        }

        this.line = line;
        this.station = station;
        this.distance = distance;
        this.line.add(this);
        this.station.add(this);
    }

    public void changeDistance(final Distance distance) {
        this.distance = distance;
    }

    public String getLineName() {
        return line.getName();
    }

    public String getStationName() {
        return station.getName();
    }

    public long getDistanceFromPreviousStation() {
        return distance.getDistanceForMeter();
    }

    public String getPreviousStationName() {
        return distance.getPreviousStationName();
    }
}
