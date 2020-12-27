package jpa.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity {

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime modifiedDate;

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * todo 삭제 필요
	 * createDate update 가능 여부 테스트용 메소드
	 */
	public void changeCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
}