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

	@Column
	private String name;

	@ManyToMany
	@JoinColumn(name = "line_id")
	private List<Line> line;

	@Column
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column
	@UpdateTimestamp
	private LocalDateTime modifiedDate;

	protected Station() {

	}

	public Station(String name) {
		this.name = name;
	}

	public void setLine(List<Line> line) {
		this.line = line;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Line> getLine() {
		return line;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	@Override
	public String toString() {
		return "Station{" +
			"id=" + id +
			", name='" + name + '\'' +
			", line=" + line +
			", createdDate=" + createdDate +
			", modifiedDate=" + modifiedDate +
			'}';
	}

	public void changeName(String name) {
		this.name = name;
	}
}
