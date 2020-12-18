package jpa.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import jpa.infrastructure.jpa.BaseEntity;
import org.springframework.util.StringUtils;

/**
 * @author : leesangbae
 * @project : jpa
 * @since : 2020-12-15
 */

@Entity
public class Line extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "line")
    private List<LineStation> lineStations = new ArrayList<>();

    protected Line() {
    }

    public Line(String name, String color) {
        validation(name, color);
        this.name = name;
        this.color = color;
    }

    public void add(LineStation lineStation) {
        this.lineStations.add(lineStation);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<LineStation> getLineStations() {
        return lineStations;
    }

    private void validation(String name, String color) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(color)) {
            throw new IllegalArgumentException("Line의 name, color는 필수 값 입니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Line line = (Line) o;
        return Objects.equals(id, line.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
