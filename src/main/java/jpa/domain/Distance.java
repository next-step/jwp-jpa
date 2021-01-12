package jpa.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Distance {

	private int distance;

	protected Distance() {
	}

	public Distance(final int distance) {
		validate(distance);
		this.distance = distance;
	}

	private void validate(final int distance) {
		if (distance <= 0) {
			throw new IllegalArgumentException();
		}
	}

	public int getDistance() {
		return distance;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final Distance distance1 = (Distance)o;

		return distance == distance1.distance;
	}

	@Override
	public int hashCode() {
		return distance;
	}
}
