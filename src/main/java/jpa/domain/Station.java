package jpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Station extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
