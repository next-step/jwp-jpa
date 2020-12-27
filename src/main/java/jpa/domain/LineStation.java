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
	@AttributeOverride(name = "distance", column = @Column(name = "prevStationDistance"))
	private Distance distance;

	protected LineStation() {
	}

	public LineStation(Line line, Station station, Distance distance) {
		this.line = line;
		this.station = station;
		this.distance = distance;
	}

	public Line getLine() {
		return this.line;
	}

	public Station getStation() {
		return this.station;
	}

	public Distance getDistance() {
		return distance;
	}
}
