package jpa.position;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jpa.common.BaseTime;
import jpa.line.Line;
import jpa.station.Station;
import lombok.Getter;
import lombok.ToString;

@ToString
@Entity
public class Position extends BaseTime {

	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	@ManyToOne
	@JoinColumn
	private Line line;

	@Getter
	@ManyToOne
	@JoinColumn
	private Station station;

	@Getter
	@Column(nullable = false)
	private int location;

	protected Position() {
	}

	public Position(int location) {
		this.location = location;
	}

	public void addSubway(Station station, Line line) {
		station.getPositions().add(this);
		line.getPositions().add(this);

		this.station = station;
		this.line = line;
	}
}
