package jpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Line extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String color;

    private String name;
}
