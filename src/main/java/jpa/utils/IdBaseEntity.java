package jpa.utils;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class IdBaseEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // for JPA
    protected IdBaseEntity() {
    }

    protected Long getId() {
        return id;
    }

    protected void setId(final Long id) {
        this.id = id;
    }
}
