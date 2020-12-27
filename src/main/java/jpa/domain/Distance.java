package jpa.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Distance {

	@Column(name = "distance", nullable = false)
	private int distance;

	protected Distance() {
	}

	public Distance(int distance) {
		validate(distance);
		this.distance = distance;
	}

	public static Distance of(int distance) {
		return new Distance(distance);
	}

	private static void validate(int distance) {
		if (distance <= 0) {
			throw new IllegalArgumentException("distance must be greater than zero!");
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Distance) {
			return Objects.equals(((Distance) obj).distance, this.distance);
		}

		return Objects.equals(obj, this);
	}
}
