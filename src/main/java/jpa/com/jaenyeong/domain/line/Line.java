package jpa.com.jaenyeong.domain.line;

import jpa.com.jaenyeong.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "LINE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public Line(final String name, final String color) {
        this.name = name;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public void changeLineColor(final String color) {
        this.color = color;
    }
}
