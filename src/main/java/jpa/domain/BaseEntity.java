package jpa.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {

    @Column
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column
    @UpdateTimestamp
    private LocalDateTime modifiedDate;

}
