package jpa.domain.station;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import jpa.domain.BaseEntity;
import jpa.domain.line.Line;

@Entity
public class Station extends BaseEntity {
	@Column(unique = true)
	private String name;

	@ManyToMany(mappedBy = "stations")
	private List<Line> lines = new ArrayList<>();

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

	public List<Line> getLines() {
		return lines;
	}
}
