package jpa.domain.station;

import jpa.domain.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Station extends BaseEntity {

    @Column(name = "name", unique=true)
    private String name;

    public Station(String name) {
        this.name = name;
    }

    public Station updateName(String name) {
        this.name = name;
        return this;
    }
}
