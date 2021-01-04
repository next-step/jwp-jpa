package jpa.position;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jpa.common.BaseTime;
import jpa.line.Line;
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
	@JoinColumn(name = "lineId")
	private Line line;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Station upStation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Station downStation;

	@Resource
	private transient Distance distance;

	public Position(Line line, Station upStation, Station downStation, long distance) {
		this.line = line;
		this.upStation = upStation;
		this.downStation = downStation;
		this.distance = new Distance(distance);
	}

	public List<Station> getStations() {
		return Arrays.asList(this.upStation, this.downStation);
	}

	public long distance() {
		return this.distance.getDistance();
	}

	public String upStationName() {
		return this.upStation.getName();
	}

	public String downStationName() {
		return this.downStation.getName();
	}
}
