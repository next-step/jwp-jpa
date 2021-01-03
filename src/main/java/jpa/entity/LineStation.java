package jpa.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class LineStation extends BaseEntity{

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
		this.pk = LineStationPk.of(builder.line, builder.station);
		this.line = builder.line;
		this.station = builder.station;
		this.upStation = builder.upStation;
		this.upDistance = builder.upDistance;
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