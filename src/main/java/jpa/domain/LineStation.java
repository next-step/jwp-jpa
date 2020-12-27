package jpa.domain;

import javax.persistence.*;

@Entity
@Table(name = "line_station")
class LineStation extends BaseEntity {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "line_id", nullable = false)
	private Line line;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "station_id", nullable = false)
	private Station station;

	@Embedded
	@AttributeOverride(name = "prevStationDistance", column = @Column(name = "prevStationDistance"))
	private Distance prevStationDistance;

	protected LineStation() {
	}

	public LineStation(Line line, Station station, Distance prevStationDistance) {
		this.line = line;
		this.station = station;
		this.prevStationDistance = prevStationDistance;
	}

	public void changePrevStationDistance(Distance distance) {
		this.prevStationDistance = distance;
	}

	public Line getLine() {
		return this.line;
	}

	public Station getStation() {
		return this.station;
	}

	public Distance getPrevStationDistance() {
		return prevStationDistance;
	}
}
