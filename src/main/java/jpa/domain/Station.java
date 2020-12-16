package jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Station extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;

	@ManyToOne
	private Line line;

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

	public void setLine(Line line) {
		this.line = line;
	}

	public String getLineName() {
		return this.line.getName();
	}
}
