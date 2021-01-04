package jpa.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Station extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;

	@OneToMany(mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
	private final Set<LineStation> lineStations = new HashSet<>();

	protected Station() {
	}

	public Station(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void changeName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Set<LineStation> getLineStations() {
		return lineStations;
	}

	public void addLineStation(Line line, Station upStation, Integer upDistance) {
		addLineStation(new LineStation.Builder()
				.line(line)
				.station(this)
				.upStation(upStation)
				.upDistance(upDistance)
			.build());
	}

	public void addLineStation(LineStation lineStation) {
		if (lineStations.add(lineStation)) {
			lineStation.addToLine(lineStation);
		}
	}

	public void removeLineStation(LineStation lineStation) {
		if (lineStations.remove(lineStation)) {
			lineStation.removeFromLine(lineStation);
		}
	}
}