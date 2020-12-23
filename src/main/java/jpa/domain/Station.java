package jpa.domain;

import static javax.persistence.FetchType.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author : byungkyu
 * @date : 2020/12/20
 * @description :
 **/
@Entity
public class Station extends BaseEntity {
	@Column(unique = true)
	private String name;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "line_id")
	private Line line;

	@OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
	private List<LineStation> lineStations = new ArrayList<>();

	public Station() {
	}

	public Station(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void changeName(String name) {
		this.name = name;
	}

	public Line getLine() {
		return line;
	}

	public void changeLine(Line line) {
		this.line = line;
	}

	public List<LineStation> getLineStations() {
		return lineStations;
	}
}
