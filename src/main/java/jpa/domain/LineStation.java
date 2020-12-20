package jpa.domain;

import javax.persistence.Embedded;
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

	@Embedded
	private Location location;

	protected LineStation() {
	}

	public LineStation(Line line, Station station) {
		this.line = line;
		this.station = station;
		this.location = new Location(null,null);
		line.add(this);
		station.add(this);
	}

	public LineStation(Line line, Station station, Location location) {
		this.line = line;
		this.station = station;
		this.location = location;
		line.add(this);
		station.add(this);
	}

	public int getDistance() {
		return location.getDistance();
	}

	public Station getPreviousStation() {
		return location.getPreviousStation();
	}

	public void deletePreviousStation() {
		this.location.setPreviousStation(null);
	}
}
