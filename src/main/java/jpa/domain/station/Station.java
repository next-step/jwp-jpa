package jpa.domain.station;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import jpa.domain.BaseEntity;
import jpa.domain.linestation.LineStation;

@Entity
public class Station extends BaseEntity {
	@Column(unique = true)
	private String name;

	@OneToMany(mappedBy = "station")
	private List<LineStation> lineStations = new ArrayList<>();

	protected Station() {
	}

	public Station(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LineStation> getLineStations() {
		return lineStations;
	}
}
