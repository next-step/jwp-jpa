package jpa.com.jaenyeong.domain.station;

import jpa.com.jaenyeong.domain.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "STATION")
public class Station extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    public Station(final String name) {
        this.name = name;
    }

    public void changeStationName(final String name) {
        this.name = name;
    }
}
