package jpa.core;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @CreatedDate
    protected LocalDateTime createdDate;
    @LastModifiedDate
    protected LocalDateTime modifiedDate;

}
