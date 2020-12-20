package jpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Favorite extends BaseTimeEntity {
    @Id @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }
}
