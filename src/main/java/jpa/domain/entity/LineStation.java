package jpa.domain.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
@IdClass(LineStationId.class)
@Table(name = "line_station")
public class LineStation extends BaseEntity {

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "line_id")
	private Line line;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "station_id")
	private Station station;

	private LineStation(Line line, Station station) {
		this.line = line;
		this.station = station;
	}

	public static LineStation create(Line line, Station station) {
		return new LineStation(line, station);
	}
}