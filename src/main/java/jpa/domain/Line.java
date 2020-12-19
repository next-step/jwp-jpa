package jpa.domain;

import jpa.utils.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Line extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    @Column(unique = true)
    private String name;

    @Embedded
    private Sections sections = new Sections();

    protected Line() {
    }

    Line(final Long id, final String color, final String name) {
        this.id = id;
        this.color = color;
        this.name = name;
    }

    public Line(final String color, final String name) {
        this(null, color, name);
    }

    public Long getId() {
        return this.id;
    }

    public void updateLine(final Line line) {
        this.createdDate = line.createdDate;
        this.modifiedDate = line.modifiedDate;
        this.name = line.name;
        this.color = line.color;
    }

    public String getColor() {
        return this.color;
    }

    public String getName() {
        return this.name;
    }

    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }

    public void addSection(final Section section) {
        this.sections.addSection(section);
    }

    public Sections getSections() {
        return sections;
    }

    public Long getFirstDistance() {
        return this.sections.firstDistance();
    }
}
