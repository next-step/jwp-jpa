package jpa.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class StationLine extends BaseEntity{
	@ManyToOne
	@JoinColumn(name = "station_id")
	private Station station;

	@ManyToOne
	@JoinColumn(name = "line_id")
	private Line line;
}
