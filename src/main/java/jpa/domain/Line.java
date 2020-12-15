package jpa.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Line {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private String color;

    @Column(unique = true)
    private String name;

    protected Line() {
    }

    public Line(LocalDateTime createdDate, LocalDateTime modifiedDate, String color, String name) {
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.color = color;
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void changeColor(final String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }
}
