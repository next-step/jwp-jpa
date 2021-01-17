package jpa.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Line extends BaseDateEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String color;
	@Column(unique = true)
	private String name;

	@OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LineStation> stations = new ArrayList<>();

	protected Line() {
	}

	public Line(String name, String color) {
		this.name = name;
		this.color = color;
	}

	public void addStation(Station station, Station previousStation, int distance) {
		LineStation lineStation = new LineStation(this, station, previousStation, distance);
		stations.add(lineStation);
		station.addLine(lineStation);
	}

	private List<Station> mapStations(){
		return stations.stream()
			.map(LineStation::getStation)
			.collect(Collectors.toList());
	}

	public List<Station> getStations() {
		return Collections.unmodifiableList(mapStations());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Line line = (Line)o;
		return Objects.equals(id, line.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
