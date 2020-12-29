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
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private String color;
	@Column(unique = true)
	private String name;

	public Line() {
	}

	public Line(String name) {
		this.name = name;
	}
}
