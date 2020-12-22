package jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "line")
public class Line extends BaseEntity {

	@Column(name = "name", nullable = false, unique = true, length = 255)
	private String name;

	@Column(name = "color", nullable = false, length = 255)
	private String color;

	Line(String name, String color) {
		this.name = name;
		this.color = color;
	}
}
