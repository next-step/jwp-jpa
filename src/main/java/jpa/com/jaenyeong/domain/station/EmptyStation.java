package jpa.com.jaenyeong.domain.station;

import jpa.com.jaenyeong.domain.mapping.LineStation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "STATION")
public class EmptyStation extends Station {
    public EmptyStation() {
        super("");
    }

    @Override
    public void changeStationName(final String name) {
    }

    @Override
    public void add(final LineStation lineStation) {
    }

    @Override
    public int haveLinesSize() {
        return 0;
    }

    @Override
    public List<String> getLinesName() {
        return new ArrayList<>();
    }
}
