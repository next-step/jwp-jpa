package jpa.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * @author : byungkyu
 * @date : 2020/12/20
 * @description :
 **/
@Entity
public class Line extends BaseEntity {

	private String color;
	@Column(unique = true)
	private String name;

	@OneToMany(mappedBy = "line", cascade = CascadeType.ALL)
	private List<LineStation> lineStations = new ArrayList<>();

	public Line() {
	}

	public Line(String name, String color) {
		this.name = name;
		this.color = color;
	}

	public List<LineStation> getLineStations() {
		return lineStations;
	}

	public String getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public void changeColor(String color) {
		this.color = color;
	}

	public void addLineStation(Station startingStation, Station station, Distance distance) {
		LineStation lineStation = new LineStation(this, startingStation, station, distance);
		lineStations.add(lineStation);
		station.getLineStations().add(lineStation);
	}

	public List<Station> getStations() {
		return lineStations.stream()
			.map(LineStation::getStation)
			.collect(Collectors.toList());
	}

	public LineStation getLineStation(Station station) {
		return lineStations.stream()
			.filter(lineStation -> lineStation.hasStation(station))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("역이름에 해당되는 지하철이 없습니다"));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Line line = (Line)o;
		return Objects.equals(getColor(), line.getColor()) && Objects.equals(getName(), line.getName())
			&& Objects.equals(getLineStations(), line.getLineStations());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getColor(), getName(), getLineStations());
	}
}