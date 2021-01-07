package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "station")
public class Station extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(mappedBy = "station")
    private LineStation lineStation;

    public Station() {
    }

    public Station(String name) {
        this.name = name;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public Long getId() {
        return super.id;
    }

    public String getName() {
        return name;
    }
}