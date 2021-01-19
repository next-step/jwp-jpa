package jpa.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "favorite")
public class Favorite extends BaseEntity {

    @ManyToOne
    private Station destination;
    @ManyToOne
    private Station source;

    protected Favorite() {
    }

    protected Favorite(Station source, Station destination) {
        this.source = source;
        this.destination = destination;
    }

    public Station getDestination() {
        return destination;
    }

    public Station getSource() {
        return source;
    }

    public void updateDestination(Station station) {
        this.destination = station;
    }
}
