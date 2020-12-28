package jpa.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "line_station")
@DynamicUpdate
class LineStation extends BaseEntity {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "line_id", nullable = false)
	private Line line;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "station_id", nullable = false)
	private Station station;

	@Embedded
	@AttributeOverride(name = "distance", column = @Column(name = "prev_distance"))
	@AssociationOverride(name = "station", joinColumns = {@JoinColumn(name = "prev_station", nullable = false)})
	private ConnectedStation prevConnectedStation;

	protected LineStation() {
	}

	public LineStation(Line line, Station station, ConnectedStation prevConnectedStation) {
		this.line = line;
		this.station = station;
		this.prevConnectedStation = prevConnectedStation;
	}

	public void changePrevStationDistance(ConnectedStation connectedStation) {
		this.prevConnectedStation = connectedStation;
	}

	public Line getLine() {
		return this.line;
	}

	public Station getStation() {
		return this.station;
	}

	public ConnectedStation getPrevConnectedStation() {
		return prevConnectedStation;
	}
}
