package jpa.domain;

import javax.persistence.*;

@Entity
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Line() {}

    public Line(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
