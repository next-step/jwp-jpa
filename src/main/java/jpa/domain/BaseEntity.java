package jpa.domain;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
	private LocalDateTime create_date;
	private LocalDateTime modified_date;

	public LocalDateTime getCreate_date() {
		return create_date;
	}

	public LocalDateTime getModified_date() {
		return modified_date;
	}
}
