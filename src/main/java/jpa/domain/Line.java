package jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Line extends BaseDateEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String color;
	@Column(unique = true)
	private String name;

	protected Line() {
	}

	public Line(String name, String color) {
		this.name = name;
		this.color = color;
	}
}
