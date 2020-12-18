package jpa.dao;

import javax.persistence.Entity;

@Entity
public class Favorite extends DefaultEntity {
    protected Favorite(){}

    public Long getId() {
        return id;
    }
}
