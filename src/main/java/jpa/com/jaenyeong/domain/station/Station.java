package jpa.com.jaenyeong.domain.station;

import jpa.com.jaenyeong.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "STATION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Station extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    public Station(final String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void changeStationName(final String name) {
        this.name = name;
    }
}
