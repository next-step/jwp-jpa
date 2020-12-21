package jpa.line;

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
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(unique = true, name = "name")
    private String name;

    @Column(name = "color")
    private String color;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

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
