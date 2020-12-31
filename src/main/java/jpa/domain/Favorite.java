package jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "favorite")
@Entity
public class Favorite extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "departure_station_id")
	private final Station departureStation;

	@ManyToOne
	@JoinColumn(name = "arrival_station_id")
	private final Station arrivalStation;

	public Favorite(final Member member, final Station departureStation, final Station arrivalStation) {
		validate(member, departureStation, arrivalStation);
		changeMember(member);
		this.departureStation = departureStation;
		this.arrivalStation = arrivalStation;
	}

	private void validate(final Member member, final Station departureStation, final Station arrivalStation) {
		if (member == null || departureStation == null || arrivalStation == null) {
			throw new IllegalArgumentException();
		}
	}

	public Long getId() {
		return id;
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

	public void changeMember(final Member member) {
		if (this.member != null) {
			this.member.getFavorites().remove(this);
		}
		this.member = member;
		member.getFavorites().add(this);
	}

	@Override
	public String toString() {
		return "Favorite{" +
			"id=" + id +
			", member=" + member +
			", departureStation=" + departureStation +
			", arrivalStation=" + arrivalStation +
			'}';
	}

}
