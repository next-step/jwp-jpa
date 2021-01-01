package jpa.domain;

import javax.persistence.*;

@Entity
@Table
public class Favorite extends Date {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Favorite() {}

    public Long getId() {
        return this.id;
    }
}
