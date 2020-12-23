package jpa.line;

import jpa.core.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * create table line (
 *     id bigint not null auto_increment,
 *     created_date datetime(6),
 *     modified_date datetime(6),
 *     color varchar(255),
 *     name varchar(255),
 *     primary key (id)
 * ) engine=InnoDB
 *
 * alter table line
 *     add constraint UK_9ney9davbulf79nmn9vg6k7tn unique (name)
 */
@Entity
@Table(name = "line")
public class Line extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    private String color;

    public Line() {
    }

    public Line(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }
}
