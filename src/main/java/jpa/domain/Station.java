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

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Station() {
	}

	public Station(String name) {
		this.name = name;
	}

}