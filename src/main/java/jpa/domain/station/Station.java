package jpa.domain.station;

import jpa.domain.base.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Table(name = "station")
public class Station extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    protected Station() {
    }

    public Station(final String name) {
        this.name = name;
    }

    public Station(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

