package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "line")
public class Line extends BaseEntity {

	@Column(name = "name", nullable = false, unique = true, length = 255)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "color", nullable = false, length = 255)
	private Color color;

	@OneToMany(mappedBy = "line", cascade = CascadeType.ALL)
	private List<LineStation> lineStations;

	protected Line() {
	}

	public Line(String name, Color color) {
		this.name = name;
		this.color = color;
		this.lineStations = new ArrayList<>();
	}

	public List<Station> getStations() {
		return this.getLineStations().stream()
				.map(LineStation::getStation)
				.collect(Collectors.toList());
	}

	public void addStation(Station station, ConnectedStation prevStationConnectedStation) {
		if (station == null) {
			throw new IllegalArgumentException("station cannot be null");
		}

		if (findLineStation(station).isPresent()) {
			throw new IllegalArgumentException("duplicated station");
		}

		LineStation lineStation = new LineStation(this, station, prevStationConnectedStation);
		this.getLineStations().add(lineStation);
		station.getLineStations().add(lineStation);
	}

	public void removeStation(Station station) {
		if (station == null) {
			throw new IllegalArgumentException("station cannot be null");
		}

		LineStation lineStation = findLineStation(station)
				.orElseThrow(() -> new IllegalArgumentException("cannot find station"));
		this.getLineStations().remove(lineStation);
		station.getLineStations().remove(lineStation);
	}

	private Optional<LineStation> findLineStation(Station station) {
		return this.getLineStations().stream()
				.filter(lineStation -> lineStation.getStation().equals(station))
				.findFirst();
	}

	List<LineStation> getLineStations() {
		return lineStations;
	}
}
