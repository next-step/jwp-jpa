package jpa.station;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * create table station (
 *     id bigint not null auto_increment,
 *     created_date datetime(6),
 *     modified_date datetime(6),
 *     name varchar(255),
 *     primary key (id)
 * ) engine=InnoDB
 *
 * alter table station
 *     add constraint UK_gnneuc0peq2qi08yftdjhy7ok unique (name)
 */
@Entity
@Table(name = "station")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "name", unique = true)
    private String name;

    public Station() {
    }

    public Station(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public String getName() {
        return name;
    }
}
