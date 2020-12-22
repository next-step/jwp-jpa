package jpa.domain;

import javax.persistence.*;

@Entity
@Table(name = "line")
public class Line extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, unique = true, length = 255)
	private String name;

	@Column(name = "color", nullable = false, length = 255)
	private String color;

	Line(String name, String color) {
		this.name = name;
		this.color = color;
	}
}
