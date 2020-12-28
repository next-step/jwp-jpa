package jpa.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author : byungkyu
 * @date : 2020/12/23
 * @description :
 **/
@Entity
public class LineStation extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "line_id")
	private Line line;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "starting_station_id")
	private Station startingStation;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "station_id")
	private Station station;

	@Embedded
	private Distance distance;

	public LineStation() {

	}

	public LineStation(Line line, Station startingStation, Station station, Distance distance) {
		this.line = line;
		this.startingStation = startingStation;
		this.station = station;
		this.distance = distance;
	}

	public Line getLine() {
		return line;
	}

	public Station getStartingStation() {
		return startingStation;
	}

	public Station getStation() {
		return station;
	}

	public Distance getDistance() {
		return distance;
	}

	public List<Station> getStations() {
		return Arrays.asList(startingStation, station);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		LineStation that = (LineStation)o;
		return Objects.equals(getLine(), that.getLine()) && Objects.equals(getStartingStation(),
			that.getStartingStation()) && Objects.equals(getStation(), that.getStation())
			&& Objects.equals(getDistance(), that.getDistance());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getLine(), getStartingStation(), getStation(), getDistance());
	}

	public boolean hasStation(Station station) {
		return this.station.equals(station);
	}
}
