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
@Table(name = "station", indexes = {
	@Index(name = "UK_station_name", columnList = "name", unique = true),
})
public class Station extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", length = 255)
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LineStation> lineStations = new ArrayList<>();

	private Station(String name) {
		this.name = name;
	}

	public static Station create(String name) {
		return new Station(name);
	}

	public void updateName(String name) {
		this.name = name;
	}

	public void addLineStation(Line line, Station preStation, Integer distance) {
		this.lineStations.add(LineStation.create(line, this, preStation, distance));
	}
}