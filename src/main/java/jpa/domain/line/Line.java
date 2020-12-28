package jpa.domain.line;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import jpa.domain.BaseEntity;

@Entity
public class Line extends BaseEntity {
	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String color;

	private String name;

	protected Line() {
	}

	public Line(String color, String name) {
		this.color = color;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
