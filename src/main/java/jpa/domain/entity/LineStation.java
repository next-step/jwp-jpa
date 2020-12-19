package jpa.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jpa.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "line_station")
public class LineStation extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "line_id", nullable = false)
	private Line line;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "station_id", nullable = false)
	private Station station;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pre_station_id")
	private Station preStation;

	@Column(name = "distance")
	private Integer distance;

	private LineStation(Line line, Station station, Station preStation, Integer distance) {
		this.line = line;
		this.station = station;
		this.preStation = preStation;
		this.distance = distance;
	}

	public static LineStation create(Line line, Station station, Station preStation, Integer distance) {
		return new LineStation(line, station, preStation, distance);
	}
}