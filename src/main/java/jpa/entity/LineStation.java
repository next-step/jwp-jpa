package jpa.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class LineStation extends BaseEntity{

	@EmbeddedId
	private LineStationPk pk;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("lineId")
	private Line line;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("stationId")
	private Station station;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "up_station_id")
	private Station upStation;

	private Integer upDistance;

	protected LineStation() {
	}

	public LineStation(Line line, Station station, Station upStation, Integer upDistance) {
		this.pk = LineStationPk.of(line, station);
		this.line = line;
		this.station = station;
		this.upStation = upStation;
		this.upDistance = upDistance;
	}

	public Line getLine() {
		return line;
	}

	public Station getStation() {
		return station;
	}

	public Station getUpStation() {
		return upStation;
	}

	public Integer getUpDistance() {
		return upDistance;
	}
}