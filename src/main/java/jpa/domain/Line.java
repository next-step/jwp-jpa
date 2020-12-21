package jpa.domain;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(indexes = @Index(name = "unique_line_name", columnList = "name", unique = true))
public class Line {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Color color;

	@Column(nullable = false)
	private String name;

	@ManyToMany(mappedBy = "lines")
	private List<Station> stations = new ArrayList<>();

	@Column
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column
	@UpdateTimestamp
	private LocalDateTime modifiedDate;

	protected Line() {

	}

	public Line(Color color, String name) {
		this.color = color;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Line{" +
			"id=" + id +
			", color=" + color +
			", name='" + name + '\'' +
			", stations=" + stations +
			", createdDate=" + createdDate +
			", modifiedDate=" + modifiedDate +
			'}';
	}

	public String getName() {
		return this.name;
	}

	public List<Station> getStations() {
		return this.stations;
	}
}
