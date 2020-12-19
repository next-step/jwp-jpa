package jpa.dao;

import javax.persistence.Entity;

@Entity
public class Station extends DefaultEntity {
    private String name;

    protected Station() {}

    public Station(String name) {
        this.name = name;
    }
}
