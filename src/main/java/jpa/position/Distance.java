package jpa.position;

import lombok.Getter;

public class Distance {

	@Getter
	private final long distance;

	public Distance(long distance) {
		if (distance <= 0) {
			throw new IllegalArgumentException("거리 범위가 잘못됬습니다.");
		}
		this.distance = distance;
	}
}
