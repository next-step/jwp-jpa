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
	private Station departureStation;

	@ManyToOne
	@JoinColumn(name = "arrival_station_id")
	private Station arrivalStation;

	protected Favorite() {
	}

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
		member.addFavorite(this);
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

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final Favorite favorite = (Favorite)o;

		if (id != null ? !id.equals(favorite.id) : favorite.id != null)
			return false;
		if (member != null ? !member.equals(favorite.member) : favorite.member != null)
			return false;
		if (departureStation != null ? !departureStation.equals(favorite.departureStation) :
			favorite.departureStation != null)
			return false;
		return arrivalStation != null ? arrivalStation.equals(favorite.arrivalStation) :
			favorite.arrivalStation == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (member != null ? member.hashCode() : 0);
		result = 31 * result + (departureStation != null ? departureStation.hashCode() : 0);
		result = 31 * result + (arrivalStation != null ? arrivalStation.hashCode() : 0);
		return result;
	}

}
