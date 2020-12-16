package jpa.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import jpa.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

	public Line(String color, String name) {
		this.color = color;
		this.name = name;
	}

	public Line updateName(String name) {
		this.name = name;
		return this;
	}
}