package jpa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * @author : byungkyu
 * @date : 2020/12/20
 * @description :
 **/
@Entity
public class Line extends BaseEntity {

	private String color;
	@Column(unique = true)
	private String name;

	public Line() {
	}

	@OneToMany(mappedBy = "line")
	private List<Station> stations = new ArrayList<>();

	public Line(String name, String color) {
		this.name = name;
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public void changeColor(String color) {
		this.color = color;
	}

	public void addStation(Station station) {
		stations.add(station);
	}

	public List<Station> getStations(){
		return stations;
	}
}
