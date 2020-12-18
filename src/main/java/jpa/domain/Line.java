package jpa.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Line {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String color;

	@Column(unique = true)
	private String name;

	private LocalDateTime createdDate;

	private LocalDateTime modifiedDate;

	protected Line() {
	}

	public Line(String name) {
		this.name = name;
	}

	public Line(String name, String color) {
		this.name = name;
		this.color = color;
	}

	public Line(String name, String color, LocalDateTime createdDate) {
		this.name = name;
		this.color = color;
		this.createdDate = createdDate;
	}

	public Long getId() {
		return id;
	}

	public String getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void changeName(String name) {
		this.name = name;
	}
}
