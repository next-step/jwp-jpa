package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "line")
public class Line extends BaseEntity {

	@Column(name = "name", nullable = false, unique = true, length = 255)
	private String name;

	@Column(name = "color", nullable = false, length = 255)
	private String color;

	@OneToMany(mappedBy = "line", cascade = CascadeType.ALL)
	private List<LineStation> lineStations;

	protected Line() {
	}

	Line(String name, String color) {
		this.name = name;
		this.color = color;
		this.lineStations = new ArrayList<>();
	}

	void addStation(Station station) {
		if (station == null) {
			throw new IllegalArgumentException("station cannot be null");
		}
		LineStation lineStation = new LineStation(this, station);
		this.getLineStations().add(lineStation);
	}

	List<Station> getStations() {
		return this.getLineStations().stream()
				.map(LineStation::getStation)
				.collect(Collectors.toList());
	}

	List<LineStation> getLineStations() {
		return lineStations;
	}
}
