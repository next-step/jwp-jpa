package jpa.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LineStation> lineStations = new ArrayList<>();

	private Line(String color, String name) {
		this.color = color;
		this.name = name;
	}

	public static Line create(String color, String name) {
		return new Line(color, name);
	}

	public Line updateName(String name) {
		this.name = name;
		return this;
	}

	public Line addLineStation(Station station, Station preStation, Integer distance) {
		this.lineStations.add(LineStation.create(this, station, preStation, distance));
		return this;
	}
}