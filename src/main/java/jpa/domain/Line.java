package jpa.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "line")
public class Line {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private String color;

    @Column(unique = true)
    private String name;

    public Line() {
    }

    public Line(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public String getColor() {
        return this.color;
    }

    public String getName() {
        return this.name;
    }
}
