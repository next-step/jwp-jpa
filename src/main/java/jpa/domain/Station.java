package jpa.domain;

import javax.persistence.*;

@Entity
@Table(name = "STATION")
public class Station extends BaseEntity{
	@Column(unique = true)
	private String name;

	public Station(){
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
}
