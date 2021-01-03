package jpa.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class LineStation extends BaseEntity {

	@EmbeddedId
	private LineStationPk pk;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("lineId")
	private Line line;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("stationId")
	private Station station;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "up_station_id")
	private Station upStation;

	private Integer upDistance;

	protected LineStation() {
	}

	private LineStation(Builder builder) {
		checkValidUpStation(builder.upStation, builder.upDistance);

		this.pk = LineStationPk.of(builder.line, builder.station);
		this.line = builder.line;
		this.station = builder.station;
		this.upStation = builder.upStation;
		this.upDistance = builder.upDistance;
	}

	private void checkValidUpStation(Station upStation, Integer upDistance) {
		if (upStation != null && upDistance == null) {
			throw new IllegalArgumentException("이전역이 존재하면서 거리가 null일 수 없습니다.");
		}
		if (upStation == null && upDistance != null) {
			throw new IllegalArgumentException("이전역이 null이면 거리가 존재할 수 없습니다.");
		}
		if (upDistance != null && upDistance <= 0) {
			throw new IllegalArgumentException("거리는 0과 같거나 작을 수 없습니다.");
		}
	}

	public Line getLine() {
		return line;
	}

	public void addToLine(LineStation lineStation) {
		line.addLineStation(lineStation);
	}

	public void removeFromLine(LineStation lineStation) {
		line.removeLineStation(lineStation);
	}

	public Station getStation() {
		return station;
	}

	public void addToStation(LineStation lineStation) {
		station.addLineStation(lineStation);
	}

	public void removeFromStation(LineStation lineStation) {
		station.removeLineStation(lineStation);
	}

	public Station getUpStation() {
		return upStation;
	}

	public Integer getUpDistance() {
		return upDistance;
	}

	public static class Builder {
		private Line line;
		private Station station;
		private Station upStation;
		private Integer upDistance;

		public Builder() {
		}

		public Builder line(Line line) {
			this.line = line;
			return this;
		}

		public Builder station(Station station) {
			this.station = station;
			return this;
		}

		public Builder upStation(Station upStation) {
			this.upStation = upStation;
			return this;
		}

		public Builder upDistance(Integer upDistance) {
			this.upDistance = upDistance;
			return this;
		}

		public LineStation build() {
			return new LineStation(this);
		}
	}
}