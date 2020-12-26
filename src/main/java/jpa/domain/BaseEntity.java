package jpa.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreatedDate
	@Column(name = "created_date", nullable = false, updatable = false, columnDefinition = "datetime")
	private LocalDateTime createdDate;

	@LastModifiedDate
	@Column(name = "modified_date", nullable = false, columnDefinition = "datetime")
	private LocalDateTime modifiedDate;

	Long getId() {
		return id;
	}

	LocalDateTime getCreatedDate() {
		return createdDate;
	}

	LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BaseEntity that = (BaseEntity) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "BaseEntity{" +
				"id=" + id +
				'}';
	}
}
