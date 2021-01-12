package jpa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "station")
@Entity
public class Station extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", unique = true)
	private String name;

	@OneToMany(mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<LineStation> lineStations = new ArrayList<>();

	protected Station() {
	}

	public Station(final String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<LineStation> getLineStations() {
		return lineStations;
	}

	public void addLineStation(LineStation lineStation) {
		this.lineStations.add(lineStation);
	}

	public int getDistance(Line line) {
		return this.lineStations.stream()
			.filter(lineStation -> lineStation.matchLine(line))
			.filter(lineStation -> lineStation.matchStation(this))
			.findFirst()
			.map(LineStation::getDistance)
			.orElseThrow(IllegalArgumentException::new);
	}

	public boolean matchName(final String name) {
		return this.name.equals(name);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final Station station = (Station)o;

		if (id != null ? !id.equals(station.id) : station.id != null)
			return false;
		return name != null ? name.equals(station.name) : station.name == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Station{" +
			"id=" + id +
			", name='" + name + '\'' +
			", lineStations=" + lineStations +
			'}';
	}

}
