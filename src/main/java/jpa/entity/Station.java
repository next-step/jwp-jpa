package jpa.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Station extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;

	@ManyToMany
	@JoinTable(
		name = "line_station",
		joinColumns = @JoinColumn(name = "station_id"),
		inverseJoinColumns = @JoinColumn(name = "line_id")
	)
	private final Set<Line> lines = new HashSet<>();

	protected Station() {
	}

	public Station(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void changeName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Set<Line> getLines() {
		return lines;
	}

	public void addLine(Line line) {
		if (lines.contains(line)) {
			return;
		}
		lines.add(line);
		line.addStation(this);
	}
}