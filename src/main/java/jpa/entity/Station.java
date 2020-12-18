package jpa.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@Entity
@Table(name = "station", uniqueConstraints = {@UniqueConstraint(
        name = "UK_gnneuc0peq2qi08yftdjhy7ok", columnNames = {"name"}
)})
public class Station extends BaseEntity {

    private String name;

    public Station(String name) {
        this.name = name;
    }
}
