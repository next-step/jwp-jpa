package jpa.dao;

import jpa.domain.Line;
import jpa.domain.LineStation;
import jpa.domain.Station;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class LineStationRepositoryTest {
    @Autowired
    private LineStationRepository lineStationRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineRepository lineRepository;

    private Station station;

    private Line line;

    @BeforeEach
    void setUp() {
        station = new Station("강남역");
        stationRepository.save(station);
        line = new Line("2호선", "green");
        lineRepository.save(line);
    }

    @Test
    @DisplayName("저장과 조회")
    void saveAndFindById() {
        Station station1 = stationRepository.save(new Station("역삼역"));
        final LineStation lineStation1 = new LineStation();
        lineStation1.setStation(station);
        lineStation1.setLine(line);
        lineStationRepository.save(lineStation1);
        final LineStation lineStation2 = new LineStation();
        lineStation2.setStation(station1);
        lineStation2.setLine(line);
        lineStationRepository.save(lineStation2);

        final List<LineStation> actual = lineStationRepository.findAll();
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual.get(0).getStation().getName()).isEqualTo("강남역");
        assertThat(actual.get(0).getLine().getName()).isEqualTo("2호선");
        assertThat(actual.get(1).getStation().getName()).isEqualTo("역삼역");
        assertThat(actual.get(1).getLine().getName()).isEqualTo("2호선");
    }

    @Test
    @DisplayName("수정")
    void modify() {
        Line line1 = lineRepository.save(new Line("신분당선", "pink"));
/*
        line1.getLineStations().get(0).changeLine(line1);
        assertThat(line1.getLineStations().size()).isEqualTo(1);
*/
    }
}
