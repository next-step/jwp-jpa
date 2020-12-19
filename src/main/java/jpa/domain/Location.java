package jpa.domain;

import javax.persistence.*;

@Entity
public class Location extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int distance;

	@JoinColumn(name = "previousStationId")
	@ManyToOne(fetch = FetchType.LAZY)
	private LineStation previousStation;

	protected Location() {
	}

	public Location(int distance, LineStation previousStation) {
		this.distance = distance;
		this.previousStation = previousStation;
	}

	public int getDistance() {
		return distance;
	}

	public LineStation getPreviousStation() {
		return previousStation;
	}
}
