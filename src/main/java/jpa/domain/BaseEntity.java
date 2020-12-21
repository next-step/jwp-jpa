package jpa.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract class BaseEntity {

	@CreatedDate
	@Column(name = "created_date", nullable = false, updatable = false, columnDefinition = "datetime")
	private LocalDateTime createdDate;

	@LastModifiedDate
	@Column(name = "modified_date", nullable = false, columnDefinition = "datetime")
	private LocalDateTime modifiedDate;

	LocalDateTime getCreatedDate() {
		return createdDate;
	}

	LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
}
