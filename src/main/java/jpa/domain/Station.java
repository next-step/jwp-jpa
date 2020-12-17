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
public class Station extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;

	@OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
	private final List<LineStation> lineStation = new ArrayList<>();

	public Station(String name) {
		this.name = name;
	}

	protected Station() {

	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void addLineStation(LineStation lineStation) {
		this.lineStation.add(lineStation);
	}

	public List<LineStation> getLineStation() {
		return lineStation;
	}
}
