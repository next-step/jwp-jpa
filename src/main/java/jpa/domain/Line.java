package jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Line extends BaseEntity{

	private String color;

	@Column(unique = true)
	private String name;

	protected Line() {
	}

	public Line(String name) {
		this.name = name;
	}

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

	public void changeName(String name) {
		this.name = name;
	}
}
