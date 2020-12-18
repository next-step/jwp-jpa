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

	public LineStation() {
	}

	public LineStation(Line line, Station station) {
		this.line = line;
		this.station = station;
		line.addLineStation(this);
		station.add(this);
	}
}
