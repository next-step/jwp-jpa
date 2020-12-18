package jpa.domain;

import javax.persistence.*;

@Entity
public class Favorite extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "departureId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Station departure;

	@JoinColumn(name = "arrivalId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Station arrival;

	@JoinColumn(name = "memberId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	protected Favorite() {
	}

	public Favorite(Station departure, Station arrival, Member member) {
		this.departure = departure;
		this.arrival = arrival;
		this.member = member;
		member.addFavorite(this);
	}

	public void setMember(Member member) {
		this.member = member;
	}


}
