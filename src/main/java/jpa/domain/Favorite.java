package jpa.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_date;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modified_date;

    public Favorite() {

    }

    public void chageDate(Date date) {
        modified_date = date;
    }

    public Long getId() {
        return id;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public Date getModified_date() {
        return modified_date;
    }
}
