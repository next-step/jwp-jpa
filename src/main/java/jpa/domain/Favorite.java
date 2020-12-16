package jpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Favorite extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "departureId")
	@OneToOne
	private Station departure;

	@JoinColumn(name = "arrivalId")
	@OneToOne
	private Station arrival;

	public void setDeparture(Station departure) {
		this.departure = departure;
	}

	public void setArrival(Station arrival) {
		this.arrival = arrival;
	}
}
