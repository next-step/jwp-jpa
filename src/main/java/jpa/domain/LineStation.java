package jpa.domain;

import javax.persistence.CascadeType;
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

	@JoinColumn(name = "locationId")
	@ManyToOne(cascade = {CascadeType.ALL})
	private Location location;

	protected LineStation() {
	}

	public LineStation(Line line, Station station) {
		this.line = line;
		this.station = station;
	}

	public LineStation(Line line, Station station, Location location) {
		this.line = line;
		this.station = station;
		this.location = location;
		line.add(this);
		station.add(this);
	}

	public Station getStation() {
		return station;
	}

	public Location getLocation() {
		return location;
	}
}
