package jpa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@Data
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(
        name = "UK_9ney9davbulf79nmn9vg6k7tn", columnNames = {"name"}
)})
public class Line extends BaseEntity {
    private String color;
    private String name;
}
