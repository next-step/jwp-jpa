package jpa.domain;

import javax.persistence.*;

@Entity
@Table(name = "favorite")
public class Favorite extends BaseEntity{

//	@Column
	@OneToOne
	private Station departureStation;

//	@Column
	@OneToOne
	private Station arrivalStation;

	public Favorite() {
	}

	public Favorite(Station departureStation, Station arrivalStation) {
		this.departureStation = departureStation;
		this.arrivalStation = arrivalStation;
	}

	public Station getDepartureStation() {
		return departureStation;
	}

	public void setDepartureStation(Station departureStation) {
		this.departureStation = departureStation;
	}

	public Station getArrivalStation() {
		return arrivalStation;
	}

	public void setArrivalStation(Station arrivalStation) {
		this.arrivalStation = arrivalStation;
	}
}
