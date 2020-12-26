package jpa.domain;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
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
