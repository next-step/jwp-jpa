package jpa.domain;

import net.bytebuddy.asm.Advice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Station {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @Column(unique = true)
    private String name;

    protected Station() {
    }

    Station(final LocalDateTime createdDate, final LocalDateTime modifiedDate, final String name) {
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.name = name;
    }

    public Station(final String name) {
        this(LocalDateTime.now(), null, name);
    }

    public void updateStation(final Station station) {
        this.modifiedDate = LocalDateTime.now();
        this.name = station.name;
    }

    public Long getId() {
        return this.id;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public String getName() {
        return name;
    }
}
