package jpa.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

	@JoinColumn(name = "memberId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	protected Favorite() {
	}

	public Favorite(Station departure, Station arrival) {
		this.departure = departure;
		this.arrival = arrival;
	}
}
