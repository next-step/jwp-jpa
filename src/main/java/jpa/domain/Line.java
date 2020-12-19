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
    private String color;
    @Column(unique = true)
    private String name;

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
}
