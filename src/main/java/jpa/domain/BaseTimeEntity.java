package jpa.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author : byungkyu
 * @date : 2020/12/20
 * @description :
 **/
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseTimeEntity {
	@CreatedBy
	@Column(updatable = false, columnDefinition = "datetime(6)")
	private LocalDateTime createDate;

	@LastModifiedDate
	@Column(updatable = false, columnDefinition = "datetime(6)")
	private LocalDateTime modifiedDate;
}
