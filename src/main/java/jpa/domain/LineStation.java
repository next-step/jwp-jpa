package jpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
	uniqueConstraints = {
		@UniqueConstraint(
			columnNames = {"line_id", "station_id"}
		),
		@UniqueConstraint(
			columnNames = {"line_id", "previous_station_id"}
		),
	}
)
public class LineStation extends BaseDateEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	private Line line;
	@ManyToOne(optional = false)
	private Station station;
	@ManyToOne
	private Station previousStation;
	private int distance;

	protected LineStation() {
	}

	public LineStation(Line line, Station station, Station previousStation, int distance) {
		this.line = line;
		this.station = station;
		this.previousStation = previousStation;
		this.distance = distance;
	}

	public Line getLine() {
		return line;
	}

	public Station getStation() {
		return station;
	}
}
