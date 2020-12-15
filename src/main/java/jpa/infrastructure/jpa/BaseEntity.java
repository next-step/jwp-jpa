package jpa.infrastructure.jpa;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author : leesangbae
 * @project : jpa
 * @since : 2020-12-15
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @CreatedDate
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    @LastModifiedDate
    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

}
