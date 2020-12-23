package jpa.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "line")
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created_date")
    private LocalDateTime createDate;
    @Column(name = "modified_date")
    private LocalDateTime updateDate;
    @Column(name = "color")
    private String color;
    @Column(name = "name", unique = true)
    private String name;

    protected Line() {
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

    public Line(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public void changeName(String name) {
        this.name = name;
    }
}
