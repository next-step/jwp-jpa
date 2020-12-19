package jpa.domain;

import javax.persistence.*;

@Entity
public class Station extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    protected Station() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
