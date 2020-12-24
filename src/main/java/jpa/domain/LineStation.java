package jpa.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class LineStation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "line_id")
	private Line line;

	@ManyToOne
	@JoinColumn(name = "station_id")
	private Station station;

	@Embedded
	private Distance distance;

	protected LineStation() {
	}

	public LineStation(Line line, Station station, int distance) {
		this.line = line;
		this.station = station;
		this.distance = new Distance(distance);
	}

	public Line getLine() {
		return line;
	}

	public Station getStation() {
		return station;
	}

	public int getDistance() {
		return distance.getDistance();
	}
}
