package jpa.domain;

import com.sun.istack.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import jpa.infrastructure.jpa.BaseEntity;
import org.springframework.util.StringUtils;

/**
 * @author : leesangbae
 * @project : jpa
 * @since : 2020-12-15
 */

@Entity
public class Station extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "station")
    private List<LineStation> lineStations = new ArrayList<>();

    protected Station() {
    }

    public Station(String name) {
        validation(name);
        this.name = name;
    }

    public void add(LineStation lineStation) {
        this.lineStations.add(lineStation);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<LineStation> getLineStations() {
        return lineStations;
    }

    private void validation(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Station의 name은 필수 값 입니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Station station = (Station) o;
        return Objects.equals(id, station.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
