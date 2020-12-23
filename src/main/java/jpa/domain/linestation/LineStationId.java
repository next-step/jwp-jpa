package jpa.domain.linestation;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class LineStationId implements Serializable {
    @Column(name = "line_id")
    Long lineId;

    @Column(name = "station_id")
    Long stationId;

    protected LineStationId() {
    }

    public LineStationId(Long lineId, Long stationId) {
        this.lineId = lineId;
        this.stationId = stationId;
    }
}
