package jpa.domain;

import javax.persistence.*;
import java.util.Objects;

@Embeddable
public class ConnectedStation {

	@Column(name = "distance", nullable = false)
	private int distance;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "station", nullable = false)
	private Station station;

	protected ConnectedStation() {
	}

	public static ConnectedStation of(int distance) {
		return new ConnectedStation(distance);
	}

	public ConnectedStation(int distance) {
		validateDistance(distance);
		this.distance = distance;
	}

	public ConnectedStation(int distance, Station station) {
		validateDistance(distance);
		this.distance = distance;
		this.station = station;
	}

	private static void validateDistance(int distance) {
		if (distance <= 0) {
			throw new IllegalArgumentException("distance must be greater than zero!");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ConnectedStation that = (ConnectedStation) o;
		return distance == that.distance &&
				Objects.equals(station, that.station);
	}

	@Override
	public int hashCode() {
		return Objects.hash(distance, station);
	}
}
