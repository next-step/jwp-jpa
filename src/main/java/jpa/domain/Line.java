package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

	public Line(String color, String name) {
		this.color = color;
		this.name = name;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Line line = (Line) o;
		return Objects.equals(color, line.color) &&
				Objects.equals(name, line.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, name);
	}
}
