package jpa.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

	@OneToMany(mappedBy = "station", orphanRemoval = true)
	private final List<LineStation> lineStations = new ArrayList<>();

	public Station(String name) {
		this.name = name;
	}

	protected Station() {

	}

	public String getName() {
		return name;
	}

	public void add(LineStation lineStation) {
		this.lineStations.add(lineStation);
	}

	public boolean isEqualsContainsLineSize(int size) {
		return lineStations.size() == size;
	}
}
