package jpa.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class LineStation extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "lineId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Line line;

	@JoinColumn(name = "stationId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Station station;

	private int distance;

	protected LineStation() {
	}

	public LineStation(Line line, Station station, int distance) {
		this.line = line;
		this.station = station;
		this.distance = distance;
		line.add(this);
		station.add(this);
	}

	public int getDistance() {
		return distance;
	}
}
