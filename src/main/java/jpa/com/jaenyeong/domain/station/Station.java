package jpa.com.jaenyeong.domain.station;

import jpa.com.jaenyeong.domain.BaseEntity;
import jpa.com.jaenyeong.domain.mapping.LineStation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "STATION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Station extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "station", orphanRemoval = true)
    private List<LineStation> lines = new ArrayList<>();

    public Station(final String name) {
        this.name = name;
    }

    public void changeStationName(final String name) {
        this.name = name;
    }

    public void add(final LineStation lineStation) {
        lines.add(lineStation);
    }

    public int haveLinesSize() {
        return lines.size();
    }

    public List<String> getLinesName() {
        return lines.stream()
            .map(LineStation::getLineName)
            .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
