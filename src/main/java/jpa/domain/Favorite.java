package jpa.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "favorite")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
    @ManyToOne
    private Station destination;
    @ManyToOne
    private Station source;

    protected Favorite(Station source, Station destination) {
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
        this.source = source;
        this.destination = destination;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public Station getDestination() {
        return destination;
    }

    public Station getSource() {
        return source;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;

    }


    public void update() {
        this.modifiedDate = LocalDateTime.now();
    }


}
