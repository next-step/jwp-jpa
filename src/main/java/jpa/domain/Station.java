package jpa.domain;

import javax.persistence.*;

@Entity
@Table(name = "STATION")
public class Station {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date created_date;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date modified_date;

	@Column(unique = true)
	private String name;

	public Station(){
	}

	public Station(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void changeName(String name) {
		this.name = name;

	}
}
