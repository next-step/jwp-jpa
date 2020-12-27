package jpa.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "station")
@DynamicUpdate
public class Station extends BaseEntity {

	@Column(name = "name", nullable = false, unique = true, length = 255)
	private String name;

	@OneToMany(mappedBy = "station", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<LineStation> lineStations;

	protected Station() {
	}

	public Station(String name) {
		this.name = name;
		this.lineStations = new ArrayList<>();
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Line> getLines() {
		return this.getLineStations().stream()
				.map(LineStation::getLine)
				.collect(Collectors.toList());
	}

	List<LineStation> getLineStations() {
		return this.lineStations;
	}

	@PreRemove
	private void removeAll() {
		for (LineStation lineStation : getLineStations()) {
			lineStation.getLine().getLineStations().remove(lineStation);
		}
	}
}
