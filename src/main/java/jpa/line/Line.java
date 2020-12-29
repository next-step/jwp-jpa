package jpa.line;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import jpa.common.BaseTime;
import jpa.position.Position;
import jpa.station.Station;
import lombok.Getter;

@Entity
@Table(indexes = @Index(name = "unique_line_name", columnList = "name", unique = true))
public class Line extends BaseTime {

	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	@Column(nullable = false)
	private Color color;

	@Getter
	@Column(nullable = false)
	private String name;

	@Getter
	@ManyToMany(mappedBy = "lines")
	private final List<Station> stations = new ArrayList<>();

	@Getter
	@OneToMany(mappedBy = "line")
	private final List<Position> positions = new ArrayList<>();

	protected Line() {
	}

	public Line(String name, Color color) {
		this.color = color;
		this.name = name;
	}

	public void addStation(Station station) {
		station.addLine(this);
	}

	public void clearStation(Station station) {
		station.clearLine(this);
	}

	public int getLocation(Station station) {
		return station.getLocation(this);
	}

	public List<String> getStationsName() {
		return this.stations.stream()
			.map(Station::getName)
			.collect(Collectors.toList());
	}
}
