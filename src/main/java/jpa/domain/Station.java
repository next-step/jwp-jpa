package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "STATION")
public class Station extends BaseEntity {
	@Column(unique = true)
	private String name;

	@ManyToMany
	private List<Line> lineList = new ArrayList<>();

	public Station() {
	}

	public Station(String name) {
		this.name = name;
	}

	public Station(String name, List<Line> lineList) {
		this.name = name;
		this.lineList = lineList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Line> getLineList() {
		return lineList;
	}

	public void setLineList(List<Line> lineList) {
		this.lineList = lineList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Station station = (Station) o;
		return Objects.equals(name, station.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
