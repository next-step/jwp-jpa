package jpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Station extends Base {

    @Column(unique = true)
    private String name;

    public Station(String name) {
        this.name = name;
    }

    public static Station of(String name) {
        return new Station(name);
    }
}
