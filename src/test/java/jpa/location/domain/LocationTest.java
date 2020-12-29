package jpa.location.domain;

import jpa.line.domain.Line;
import jpa.line.domain.LineColor;
import jpa.line.domain.LineRepository;
import jpa.station.domain.Distance;
import jpa.station.domain.Station;
import jpa.station.domain.StationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class LocationTest {
    @Autowired
    private LocationRepository locations;
    @Autowired
    private LineRepository lines;
    @Autowired
    private StationRepository stations;

    @DisplayName("`Location` 저장")
    @Test
    void save() {
        // Given
        Line line = lines.save(new Line(LineColor.GREEN, "2호선"));
        Station station = stations.save(new Station("잠실"));
        Station previousStation = stations.save(new Station("잠실새내"));
        // When
        Distance distance = new Distance(previousStation, 2000);
        Location location = locations.save(new Location(line, station, distance));
        // Then
        assertNotNull(location);
    }
}
