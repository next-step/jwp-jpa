package jpa.domain.linestation;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import jpa.domain.BaseEntity;
import jpa.domain.line.Line;
import jpa.domain.station.Station;

@Entity
public class LineStation extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	private Line line;

	@ManyToOne(fetch = FetchType.LAZY)
	private Station station;

	@OneToOne(fetch = FetchType.LAZY)
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

	public void setLine(Line line) {
		this.line = line;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public Station getPreviousStation() {
		return previousStation;
	}

	public void setPreviousStation(Station previousStation) {
		this.previousStation = previousStation;
	}
}
