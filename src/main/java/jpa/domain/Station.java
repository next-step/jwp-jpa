package jpa.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "station")
@EntityListeners(AuditingEntityListener.class)
public class Station {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, unique = true, length = 255)
	private String name;

	@CreatedDate
	@Column(name = "created_date", nullable = false, updatable = false, columnDefinition = "datetime")
	private LocalDateTime createdDate;

	@LastModifiedDate
	@Column(name = "modified_date", nullable = false, columnDefinition = "datetime")
	private LocalDateTime modifiedDate;

	public Station() {
	}

	Station(String name) {
		this.name = name;
	}

	Long getId() {
		return id;
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	LocalDateTime getCreatedDate() {
		return createdDate;
	}

	LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
}
