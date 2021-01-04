package jpa.line;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
import jpa.station.Station;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
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
	@OneToMany(mappedBy = "line", cascade = CascadeType.ALL)
	private final List<Position> positions = new ArrayList<>();

	public Line(String name, Color color) {
		this.color = color;
		this.name = name;
	}

	public Line(String name, Color color, Station upStation, Station downStation, long distance) {
		this(name, color);
		this.addPosition(upStation, downStation, distance);
	}

	private void addPosition(Station upStation, Station downStation, long distance) {
		this.positions.add(new Position(this, upStation, downStation, distance));
	}

	public List<Station> getStations() {
		Set<Station> result = new LinkedHashSet<>();
		for (Position position : this.positions) {
			result.addAll(position.getStations());
		}
		return new ArrayList<>(result);
	}
}
