package jpa.com.jaenyeong.domain.line;

import jpa.com.jaenyeong.domain.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
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

    public Line(final String color, final String name) {
        this.color = color;
        this.name = name;
    }

    public void changeLineColor(final String color) {
        this.color = color;
    }
}
