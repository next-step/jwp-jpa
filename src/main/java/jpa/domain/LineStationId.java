package jpa.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class LineStationId implements Serializable {

    private static final long serialVersionUID = 5427144341046976387L;

    @Column(name = "line_id")
    private Long lineId;

    @Column(name = "station_id")
    private Long stationId;

    public LineStationId() {

    }

    public LineStationId(Long lineId, Long stationId) {
        this.lineId = lineId;
        this.stationId = stationId;
    }

}
