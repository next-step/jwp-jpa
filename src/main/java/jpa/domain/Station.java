package jpa.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "station")
public class Station extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "station")
    private List<LineStation> lineStations = new ArrayList<>();

    public Station(final String name) {
        this.name = name;
    }

    public Station changeName(final String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("옯바른 역 이름이 아닙니다.");
        }
        this.name = name;
        return this;
    }

    public List<Line> lines() {
        return lineStations.stream()
                .map(LineStation::getLine)
                .collect(Collectors.toList());
    }

    public boolean isName(final String stationName) {
        return this.name.equals(stationName);
    }
}
