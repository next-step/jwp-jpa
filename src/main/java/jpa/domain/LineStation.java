package jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "line_station")
@Entity
public class LineStation extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "line_id")
	private Line line;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "station_id")
	private Station station;

	protected LineStation() {
	}

	public LineStation(Line line, Station station) {
		validate(line, station);
		this.station = station;
		this.line = line;

		line.addLineStation(this);
		station.addLineStation(this);
	}

	private void validate(Line line, Station station) {
		if (line == null || station == null) {
			throw new IllegalArgumentException();
		}
	}

	public Station getStation() {
		return station;
	}

	public Line getLine() {
		return line;
	}

}
