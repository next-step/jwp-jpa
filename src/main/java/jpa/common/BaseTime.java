package jpa.common;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @MappedSuperclass : Jpa Entity 클래스들이 이 클래스를 상속할 경우, 이 클래스의 필드들도 컬럼으로 인식
 * @EntityListeners : Auditing(감시) 기능 추가: Spring Data JPA 에서 자동으로 시간에 대한 값을 넣어주는 기능
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTime {

	// Entity 생성되어 저장될 때 시간이 자동 저장
	@CreatedDate
	private LocalDateTime createdDate;

	// 조회한 Entity 값을 변경할 때 시간이 자동 저장
	@LastModifiedDate
	private LocalDateTime modifiedDate;

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
}
