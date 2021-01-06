package jpa.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
public class Favorite extends Common{
    public Favorite() {
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public LocalDateTime getModified_date() {
        return modified_date;
    }

    public void chageDate(ZoneId zone) {
        modified_date = LocalDateTime.now(zone);
    }
}
