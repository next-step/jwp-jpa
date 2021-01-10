package jpa.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
	uniqueConstraints = {
		@UniqueConstraint(
			columnNames = {"member_id", "departure_id", "destination_id"}
		)
	}
)
public class Favorite extends BaseDateEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	private Member member;
	@ManyToOne(optional = false)
	private Station departure;
	@ManyToOne(optional = false)
	private Station destination;

	protected Favorite() {
	}

	public Favorite(Station departure, Station destination) {
		this.departure = departure;
		this.destination = destination;
	}

	public Long getId() {
		return id;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Favorite favorite = (Favorite)o;
		return Objects.equals(id, favorite.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
