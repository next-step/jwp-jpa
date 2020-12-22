package jpa.com.jaenyeong.domain.line;

import jpa.com.jaenyeong.domain.BaseEntity;
import jpa.com.jaenyeong.domain.mapping.LineStation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "LINE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Line extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private LineColor color;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "line", orphanRemoval = true)
    private List<LineStation> stations = new ArrayList<>();

    public Line(final String name, final String color) {
        this.name = name;
        this.color = getColor(color);
    }

    private LineColor getColor(final String color) {
        return LineColor.getColor(color);
    }

    public void changeLineColor(final String color) {
        this.color = getColor(color);
    }

    public void add(final LineStation lineStation) {
        stations.add(lineStation);
    }

    public int haveStationsSize() {
        return stations.size();
    }

    public List<String> getStationsName() {
        return stations.stream()
            .map(LineStation::getStationName)
            .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getColor() {
        return color.getColorName();
    }

    public String getName() {
        return name;
    }
}
