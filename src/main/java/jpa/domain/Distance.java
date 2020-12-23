package jpa.domain;

import javax.persistence.Embeddable;

/**
 * @author : byungkyu
 * @date : 2020/12/23
 * @description :
 **/
@Embeddable
public class Distance {
	private long value;

	public Distance() {
	}

	public Distance(long value) {
		this.value = value;
	}

	public static Distance of(long value) {
		return new Distance(value);
	}
}
