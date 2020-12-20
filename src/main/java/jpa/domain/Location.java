package jpa.domain;

import javax.persistence.*;

@Embeddable
public class Location {

	private Integer distance;

	@JoinColumn(name = "previousStationId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Station previousStation;

	protected Location() {
	}

	public Location(Integer distance, Station previousStation) {
		this.distance = distance;
		this.previousStation = previousStation;
	}

	public int getDistance() {
		return distance;
	}

	public Station getPreviousStation() {
		return previousStation;
	}
}
