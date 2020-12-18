package jpa.com.jaenyeong.domain.line;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @CreatedDate
    @Column(nullable = false)
    LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    LocalDateTime modifiedDate;

    @Column
    String color;

    @Column(unique = true)
    String name;

    public Line(final String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
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
