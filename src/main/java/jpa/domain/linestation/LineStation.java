package jpa.domain.linestation;

import jpa.domain.base.BaseEntity;
import jpa.domain.line.Line;
import jpa.domain.station.Station;
import lombok.*;

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

    @Embedded
    private PreStationInfo preStationInfo;

    @Builder
    private LineStation(Line line, Station station, PreStationInfo preStationInfo) {
        this.line = line;
        this.station = station;
        this.preStationInfo = preStationInfo;
    }

    public int getDistance() {
        return preStationInfo.getDistance();
    }

    public Station getPreStation() {
        return preStationInfo.getPreStation();
    }
}
