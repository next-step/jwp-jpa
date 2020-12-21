package jpa.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Line extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;

	private String color;

	@OneToMany(mappedBy = "line", orphanRemoval = true)
	private final List<LineStation> lineStations = new ArrayList<>();

	public Line(String name, String color) {
		this.name = name;
		this.color = color;
	}

	protected Line() {
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public void add(LineStation lineStation) {
		lineStations.add(lineStation);
	}

	public boolean isEqualsContainsStationSize(int size) {
		return lineStations.size() == size;
	}
}
