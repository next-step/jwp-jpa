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

	protected LineStation() {
	}

	LineStation(Line line, Station station) {
		this.line = line;
		this.station = station;
	}

	Line getLine() {
		return this.line;
	}

	Station getStation() {
		return this.station;
	}
}
