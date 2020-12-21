package jpa.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
