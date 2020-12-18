package jpa.domain;

import jpa.utils.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Station extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    protected Station() {
    }

    Station(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Station(final String name) {
        this(null, name);
    }

    public void updateStation(final Station station) {
        this.name = station.name;
    }

    public Long getId() {
        return this.id;
    }

    public LocalDateTime getModifiedDate() {
        return super.modifiedDate;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedDate() {
        return super.createdDate;
    }
}
