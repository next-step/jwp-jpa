package jpa.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * @author : byungkyu
 * @date : 2020/12/20
 * @description :
 **/
@Entity
public class Favorite extends BaseEntity {

	public Favorite() {

	}

	/*
	public Favorite(Station startingStation, Station destinationStation) {
		this.startingStation = startingStation;
		this.destinationStation = destinationStation;
	}

	public Station getStartingStation() {
		return startingStation;
	}

	public Station getDestinationStation() {
		return destinationStation;
	}*/
}
