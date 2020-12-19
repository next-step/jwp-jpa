package jpa.com.jaenyeong.domain.line;

import jpa.com.jaenyeong.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Line extends BaseEntity {
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
