package jpa.domain;

import javax.persistence.*;

@Entity
@Table(name = "station")
public class Station extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, unique = true, length = 255)
	private String name;

	public Station() {
	}

	Station(String name) {
		this.name = name;
	}

	Long getId() {
		return id;
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}
}
