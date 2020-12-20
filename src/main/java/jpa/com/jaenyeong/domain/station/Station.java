package jpa.com.jaenyeong.domain.station;

import jpa.com.jaenyeong.domain.BaseEntity;

import javax.persistence.*;

@Entity
public class Station extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    public Station(final String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Station setName(final String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }
}
