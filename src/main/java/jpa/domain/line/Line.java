package jpa.domain.line;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import jpa.domain.BaseEntity;
import jpa.domain.linestation.LineStation;

@Entity
public class Line extends BaseEntity {
	@Column(unique = true)
	private String color;

	private String name;

	@OneToMany(mappedBy = "line")
	private List<LineStation> lineStations = new ArrayList<>();

	protected Line() {
	}

	public Line(String color, String name) {
		this.color = color;
		this.name = name;
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

	public List<LineStation> getLineStations() {
		return lineStations;
	}
}
