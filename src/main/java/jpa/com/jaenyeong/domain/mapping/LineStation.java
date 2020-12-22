package jpa.com.jaenyeong.domain.mapping;

import jpa.com.jaenyeong.domain.BaseEntity;
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

    public LineStation(final Line line, final Station station) {
        if (line == null || station == null) {
            throw new IllegalArgumentException("invalid parameter");
        }

        this.line = line;
        this.station = station;
        this.line.add(this);
        this.station.add(this);
    }

    public String getLineName() {
        return line.getName();
    }

    public String getStationName() {
        return station.getName();
    }
}
