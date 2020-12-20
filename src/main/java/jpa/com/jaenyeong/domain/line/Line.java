package jpa.com.jaenyeong.domain.line;

import jpa.com.jaenyeong.domain.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "LINE")
public class Line extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    @Column(unique = true)
    private String name;

    public Line(final String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public Line setColor(final String color) {
        this.color = color;
        return this;
    }

    public String getName() {
        return name;
    }
}
