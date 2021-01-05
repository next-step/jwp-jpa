package jpa.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "station")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_date;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modified_date;

    private String name;

    public Station() {
    }

    public Station(String name) {
        this.name = name;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
