package jpa.favorite;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import jpa.common.BaseTime;
import jpa.station.Station;
import lombok.Getter;

@Entity
public class Favorite extends BaseTime {

	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	@OneToOne
	@JoinColumn(nullable = false)
	private Station startStation;

	@Getter
	@OneToOne
	@JoinColumn(nullable = false)
	private Station endStation;

	protected Favorite() {
	}

	public Favorite(Station start, Station end) {
		this.startStation = start;
		this.endStation = end;
	}

	public String startName() {
		return startStation.getName();
	}

	public String endName() {
		return endStation.getName();
	}
}
