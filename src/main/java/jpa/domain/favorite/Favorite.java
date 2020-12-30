package jpa.domain.favorite;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import jpa.domain.BaseEntity;
import jpa.domain.member.Member;
import jpa.domain.station.Station;

@Entity
public class Favorite extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	private Station departureStation;

	@ManyToOne(fetch = FetchType.LAZY)
	private Station arrivalStation;

	protected Favorite() {
	}

	public Favorite(Member member, Station departureStation, Station arrivalStation) {
		this.member = member;
		this.departureStation = departureStation;
		this.arrivalStation = arrivalStation;
	}

	public Member getMember() {
		return member;
	}

	public Station getDepartureStation() {
		return departureStation;
	}

	public Station getArrivalStation() {
		return arrivalStation;
	}
}
