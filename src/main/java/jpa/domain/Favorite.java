package jpa.domain;

import javax.persistence.*;

@Entity
@Table(name = "favorite")
class Favorite extends BaseEntity {

	@ManyToOne(targetEntity = Member.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne(targetEntity = Station.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "departure_station_id", nullable = false)
	private Station departureStation;

	@ManyToOne(targetEntity = Station.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "arrival_station_id", nullable = false)
	private Station arrivalStation;

	protected Favorite() {
	}

	Favorite(Member member, Station departureStation, Station arrivalStation) {
		this.member = member;
		this.member.getFavorites().add(this);
		this.departureStation = departureStation;
		this.arrivalStation = arrivalStation;
	}

	void changeMember(Member member) {
		if (member == null) {
			throw new IllegalArgumentException("member cannot be null");
		}
		this.member.getFavorites().remove(this);
		this.member = member;
		this.member.getFavorites().add(this);
	}

	Member getMember() {
		return this.member;
	}

	Station getDepartureStation() {
		return this.departureStation;
	}

	Station getArrivalStation() {
		return this.arrivalStation;
	}
}
