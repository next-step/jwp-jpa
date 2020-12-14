package jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import jpa.domain.common.BaseEntity;

@Entity
@Table(name = "line", indexes = {
	@Index(name = "UK_line_name", columnList = "name", unique = true),
})
public class Line extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "color", length = 255)
	private String color;

	@Column(name = "name", length = 255)
	private String name;

	public Long getId() {
		return id;
	}

	public String getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public Line() {
	}

	public Line(String color, String name) {
		this.color = color;
		this.name = name;
	}

	public Line updateName(String name) {
		this.name = name;
		return this;
	}
}