package jpa.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @author : byungkyu
 * @date : 2020/12/20
 * @description :
 **/
@Entity
public class Favorite extends BaseEntity {

	@OneToOne
	@JoinColumn(name = "starting_station_id")
	private Station startingStation;

	@OneToOne
	@JoinColumn(name = "destination_station_id")
	private Station destinationStation;

	public Favorite() {

	}

	public Favorite(Station startingStation, Station destinationStation) {
		this.startingStation = startingStation;
		this.destinationStation = destinationStation;
	}

	public Station getStartingStation() {
		return startingStation;
	}

	public Station getDestinationStation() {
		return destinationStation;
	}
}
