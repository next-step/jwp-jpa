package jpa.step1.domain;

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

import jpa.step2.domain.LineStation;

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

}
