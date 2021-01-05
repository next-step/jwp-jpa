package jpa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "line")
@Entity
public class Line extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "color")
	@Enumerated(EnumType.STRING)
	private Color color;

	@OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<LineStation> lineStations = new ArrayList<>();

	protected Line() {
	}

	public Line(final Color color, final String name) {
		this.color = color;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public List<LineStation> getLineStations() {
		return lineStations;
	}

	public void addLineStation(LineStation lineStation) {
		this.lineStations.add(lineStation);
	}

}

