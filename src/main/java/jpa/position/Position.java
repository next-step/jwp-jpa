package jpa.position;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jpa.common.BaseTime;
import jpa.station.Station;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Position extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Station station;
	@Column(nullable = false)
	private long lineId;
	@Column(nullable = false)
	private long distance;

	public Position(Station station, long id, long distance) {
		this.station = station;
		this.lineId = id;
		this.distance = distance;
	}

	public void addStation(final Station station) {
		station.getPositions().add(this);
		this.station = station;
	}
}
