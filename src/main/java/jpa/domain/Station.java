package jpa.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Station extends BaseEntity{

	@Column(unique = true)
	private String name;

	@OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
	private List<LineStation> lines = new ArrayList<>();

	protected Station() {
	}

	public Station(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Line> getLines() {
		return this.lines.stream()
			.map(LineStation::getLine)
			.collect(Collectors.toList());
	}

	public void changeName(String name) {
		this.name = name;
	}

	public void addLine(Line line, int distance) {
		if (isExistLine(line)) {
			throw new IllegalArgumentException(String.format("%s 노선은 %s 역이 이미 포함된 노선입니다.", line.getName(), this.name));
		}
		this.lines.add(new LineStation(line, this, distance));
	}

	public int distanceFromPreviousStation(Line line) {
		return this.lines.stream()
			.filter(lineStation -> lineStation.getLine().equals(line))
			.map(LineStation::getDistance)
			.findFirst()
			.orElseThrow(() ->
				new IllegalArgumentException(String.format("%s 노선은 %s 역이 포함되지 않는 노선입니다.", line.getName(), this.name))
			);
	}

	private boolean isExistLine(Line line) {
		return this.lines.stream()
			.anyMatch(lineStation -> lineStation.getLine().equals(line));
	}
}
