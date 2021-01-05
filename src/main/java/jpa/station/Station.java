package jpa.station;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import jpa.common.BaseTime;
import jpa.position.Position;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(indexes = @Index(name = "unique_station_name", columnList = "name", unique = true))
public class Station extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "upStation")
	private final List<Position> upPositions = new ArrayList<>();

	@OneToMany(mappedBy = "downStation")
	private final List<Position> downPositions = new ArrayList<>();

	public Station(String name) {
		this.name = name;
	}

	/*
	public void addLine(final Line line) {
		line.getStations().add(this);
		this.lines.add(line);
	}

	public void clearLine(final Line line) {
		line.getStations().remove(this);
		this.lines.remove(line);
	}

	public List<String> getLinesName() {
		return lines.stream()
			.map(Line::getName)
			.collect(Collectors.toList());
	}
	*/
}
