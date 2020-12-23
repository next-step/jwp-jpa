package jpa.core;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "created_date")
    protected LocalDateTime createdDate;

    @Column(name = "modified_date")
    protected LocalDateTime modifiedDate;

}
