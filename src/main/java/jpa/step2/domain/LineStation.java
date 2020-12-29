package jpa.step2.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jpa.step1.domain.BaseEntity;
import jpa.step1.domain.Line;
import jpa.step1.domain.Station;

@Table(name = "line_station")
@Entity
public class LineStation extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "line_id")
	private final Line line;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "station_id")
	private final Station station;

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
