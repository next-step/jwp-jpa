package jpa.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Distance {

	String beforeStationName;

	int distanceMeter;

	public Distance() {
	}

	public Distance(String beforeStationName, int distanceMeter) {
		this.beforeStationName = beforeStationName;
		this.distanceMeter = distanceMeter;
	}
}
