package jpa.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prev_station_id")
	private Station previousStation;

	@Embedded
	private Distance distance;

	protected LineStation() {
	}

	public LineStation(Line line, Station station, Station previousStation, int distance) {
		validate(line, station, previousStation);
		this.station = station;
		this.previousStation = previousStation;
		this.line = line;
		this.distance = new Distance(distance);

		line.addLineStation(this);
		station.addLineStation(this);
	}

	private void validate(Line line, Station station, Station previousStation) {
		if (line == null || station == null || previousStation == null) {
			throw new IllegalArgumentException();
		}
	}

	public Station getStation() {
		return station;
	}

	public Station getPreviousStation() {
		return previousStation;
	}

	public int getDistance() {
		return distance.getDistance();
	}

	public Line getLine() {
		return line;
	}

	public boolean matchStationByName(final String name) {
		return this.station.matchName(name);
	}

	public boolean matchLine(final Line line) {
		return this.line == line;
	}

	public boolean matchStation(final Station station) {
		return this.station == station;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final LineStation that = (LineStation)o;

		if (id != null ? !id.equals(that.id) : that.id != null)
			return false;
		if (line != null ? !line.equals(that.line) : that.line != null)
			return false;
		if (station != null ? !station.equals(that.station) : that.station != null)
			return false;
		return previousStation != null ? previousStation.equals(that.previousStation) : that.previousStation == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (line != null ? line.hashCode() : 0);
		result = 31 * result + (station != null ? station.hashCode() : 0);
		result = 31 * result + (previousStation != null ? previousStation.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "LineStation{" +
			"id=" + id +
			", line=" + line +
			", station=" + station +
			", previousStation=" + previousStation +
			", distance=" + distance +
			'}';
	}
}
