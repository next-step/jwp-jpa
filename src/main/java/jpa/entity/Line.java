package jpa.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Line extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;

	private String color;

	@OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
	private final Set<LineStation> lineStations = new HashSet<>();

	protected Line() {
	}

	public Line(String name, String color) {
		this.name = name;
		this.color = color;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public void changeColor(String color) {
		this.color = color;
	}

	public Set<LineStation> getLineStations() {
		return lineStations;
	}

	public void addLineStation(Station station, Station upStation, Integer upDistance) {
		addLineStation(new LineStation.Builder()
				.line(this)
				.station(station)
				.upStation(upStation)
				.upDistance(upDistance)
			.build());
	}

	public void addLineStation(LineStation lineStation) {
		if (lineStations.add(lineStation)) {
			lineStation.addToStation(lineStation);
		}
	}

	public void removeLineStation(LineStation lineStation) {
		if (lineStations.remove(lineStation)) {
			lineStation.removeFromStation(lineStation);
		}
	}
}