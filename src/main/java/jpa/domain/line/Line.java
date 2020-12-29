package jpa.domain.line;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import jpa.domain.BaseEntity;
import jpa.domain.station.Station;

@Entity
public class Line extends BaseEntity {
	@Column(unique = true)
	private String color;

	private String name;

	@ManyToMany
	private List<Station> stations = new ArrayList<>();

	protected Line() {
	}

	public Line(String color, String name) {
		this.color = color;
		this.name = name;
	}

	public Line(String color, String name, List<Station> stations) {
		this.color = color;
		this.name = name;
		this.stations = stations;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Station> getStations() {
		return stations;
	}
}
