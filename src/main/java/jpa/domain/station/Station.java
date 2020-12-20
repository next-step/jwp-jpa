package jpa.domain.station;

import jpa.domain.BaseEntity;

import javax.persistence.*;

@Entity
public class Station extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(unique = true)
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    protected Station() {
    }

    public Station(String name) {
        this.name = name;
    }
}
