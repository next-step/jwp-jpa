package jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Line extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;

	private String color;

	@OneToMany(mappedBy = "line")
	private List<Station> station = new ArrayList<>();

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

	public void addStation(Station station) {
		station.setLine(this);
		this.station.add(station);
	}

	public void addAllStation(Set<Station> stations) {
		stations.forEach(addStation -> {
			addStation.setLine(this);
			this.station.add(addStation);
		});
	}

	public List<Station> getStation() {
		return station;
	}
}
