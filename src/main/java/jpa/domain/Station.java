package jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "station")
public class Station extends BaseEntity {

	@Column(name = "name", nullable = false, unique = true, length = 255)
	private String name;

	Station() {
	}

	Station(String name) {
		this.name = name;
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}
}
