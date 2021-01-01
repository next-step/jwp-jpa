package jpa.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class LineStationPk implements Serializable {
	public Long lineId;
	public Long stationId;

	protected LineStationPk() {
	}

	private LineStationPk(Long lineId, Long stationId) {
		this.lineId = lineId;
		this.stationId = stationId;
	}

	public static LineStationPk of(Line line, Station station) {
		return new LineStationPk(line.getId(), station.getId());
	}
}