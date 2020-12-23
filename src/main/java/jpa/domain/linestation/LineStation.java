package jpa.domain.linestation;

import jpa.domain.base.BaseEntity;
import jpa.domain.line.Line;
import jpa.domain.station.Station;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "line_station")
@Entity
public class LineStation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id")
    private Line line;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    private LineStation(Line line, Station station) {
        this.line = line;
        this.station = station;
    }

    public static LineStation of(Line line, Station station) {
        return new LineStation(line, station);
    }
}
