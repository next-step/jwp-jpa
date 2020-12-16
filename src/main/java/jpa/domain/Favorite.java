package jpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Favorite {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    protected Favorite() {
        this(null, LocalDateTime.now(), null);
    }

    Favorite(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public void updateFavorite(Favorite favorite) {
        this.modifiedDate = LocalDateTime.now();
    }

    public Long getId() {
        return this.id;
    }

    public LocalDateTime getModifiedDate() {
        return this.modifiedDate;
    }
}
