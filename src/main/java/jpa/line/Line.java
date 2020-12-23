package jpa.line;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import jpa.common.BaseTime;
import jpa.station.Station;
import lombok.Getter;

@Entity
@Table(indexes = @Index(name = "unique_line_name", columnList = "name", unique = true))
public class Line extends BaseTime {

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
	private List<Station> stations = new ArrayList<>();

	protected Line() {
	}

	public Line(Color color, String name) {
		this.color = color;
		this.name = name;
	}

	public void addStation(Station station) {
		station.addLine(this);
	}

	public void clearStation(Station station) {
		station.clearLine(this);
	}
}
