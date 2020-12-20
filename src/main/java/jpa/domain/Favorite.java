package jpa.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Favorite extends BaseEntity {

	@ManyToOne
	private Station startStation;

	@ManyToOne
	private Station destinationStation;

	protected Favorite() {
	}

	public Favorite(Station startStation, Station destinationStation) {
		this.startStation = startStation;
		this.destinationStation = destinationStation;
	}

	public Station getStartStation() {
		return startStation;
	}

	public Station getDestinationStation() {
		return destinationStation;
	}
}
