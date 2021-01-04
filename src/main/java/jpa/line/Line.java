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
import javax.persistence.Table;

import jpa.common.BaseTime;
import jpa.position.Position;
import jpa.station.Station;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(indexes = @Index(name = "unique_line_name", columnList = "name", unique = true))
public class Line extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Color color;
	@Column(nullable = false)
	private String name;
	@ManyToMany(mappedBy = "lines")
	private final List<Station> stations = new ArrayList<>();

	public Line(String name, Color color) {
		this.color = color;
		this.name = name;
	}

	public void clearStation(Station station) {
		station.clearLine(this);
	}

	public List<String> getStationsName() {
		return this.stations.stream()
			.map(Station::getName)
			.collect(Collectors.toList());
	}

	public void addStation(Station station, Position position) {
		station.addPosition(position);
		changeStation(station);
	}

	private void changeStation(Station station) {
		station.addLine(this);
	}
}
