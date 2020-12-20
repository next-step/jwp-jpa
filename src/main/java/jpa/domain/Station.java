package jpa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Station extends BaseEntity{

	@Column(unique = true)
	private String name;

	@ManyToMany
	private List<Line> lines = new ArrayList<>();

	protected Station() {
	}

	public Station(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Line> getLines() {
		return lines;
	}

	public void changeName(String name) {
		this.name = name;
	}

	public void addLines(List<Line> lines) {
		for(Line line : lines) {
			this.addLine(line);
		}
	}

	public void addLine(Line line) {
		line.getStations().remove(this);
		this.lines.add(line);
		line.getStations().add(this);
	}
}
