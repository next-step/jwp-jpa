package jpa.domain;

import javax.persistence.*;

@Entity
@Table(name = "station")
public class Station extends BaseEntity{
    @Column(unique = true)
    private String name;

    protected Station() {}

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
