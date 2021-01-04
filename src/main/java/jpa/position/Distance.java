package jpa.position;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Distance {

	@Column
	private long distance;

	public Distance(long distance) {
		if (distance <= 0) {
			throw new IllegalArgumentException("거리 범위가 잘못됬습니다.");
		}
		this.distance = distance;
	}
}
