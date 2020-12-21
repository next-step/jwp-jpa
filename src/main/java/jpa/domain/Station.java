package jpa.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(indexes = @Index(name = "unique_station_name", columnList = "name", unique = true))
public class Station {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@ManyToMany
	@JoinColumn(name = "line_id")
	private List<Line> lines;

	@CreationTimestamp
	private LocalDateTime createdDate;

	@UpdateTimestamp
	private LocalDateTime modifiedDate;

	@Override
	public String toString() {
		return "Station{" +
			"id=" + id +
			", name='" + name + '\'' +
			", line=" + lines +
			", createdDate=" + createdDate +
			", modifiedDate=" + modifiedDate +
			'}';
	}

	protected Station() {

	}

	public Station(String name) {
		this.name = name;
	}

	public Station(String name, List<Line> line) {
		this.name = name;
		this.lines = line;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Line> getLines() {
		return lines;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setLines(List<Line> line) {
		this.lines = line;
	}

	public void changeName(final String name) {
		this.name = name;
	}

	public void changeId(final Long id) {
		this.id = id;
	}

	public Line getFirstLine() {
		return this.lines.stream()
			.findFirst()
			.orElse(null);
	}
}
