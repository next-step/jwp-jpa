package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "line")
public class Line extends BaseEntity{
	@Column
	private String color;

	@Column(unique = true)
	private String name;

	@ManyToMany
	private List<Station> stationList = new ArrayList<>();

	public Line() {
	}

	public Line(String color, String name, List<Station> stationList) {
		this.color = color;
		this.name = name;
		this.stationList = stationList;
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

	public List<Station> getStationList() {
		return stationList;
	}

	public void setStationList(List<Station> stationList) {
		this.stationList = stationList;
	}
}
