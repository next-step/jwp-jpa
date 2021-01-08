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
        final LineStation lineStation1 = new LineStation();
        lineStation1.setStation(station);
        lineStation1.setLine(line);
        lineStationRepository.save(lineStation1);
    }

    @Test
    @DisplayName("저장과 조회")
    void saveAndFindById() {
        Station station1 = stationRepository.save(new Station("역삼역"));
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
    @DisplayName("노선 수정")
    void modifyLine() {
        final LineStation lineStation = lineStationRepository.findByStation(station);
        Line line1 = lineRepository.save(new Line("신분당선", "pink"));
        lineStation.setLine(line1);
        LineStation actual = lineStationRepository.save(lineStation);
        lineStationRepository.flush();
        assertThat(actual.getLine().getName()).isEqualTo("신분당선");
    }

    @Test
    @DisplayName("지하철역 수정")
    void modifySubway() {
        final LineStation lineStation = lineStationRepository.findByLine(line);
        Station station1 = stationRepository.save(new Station("선릉역"));
        lineStation.setStation(station1);
        LineStation actual = lineStationRepository.save(lineStation);
        lineStationRepository.flush();
        assertThat(actual.getStation().getName()).isEqualTo("선릉역");
    }

    @Test
    @DisplayName("노선 삭제")
    void removeLine() {
        final LineStation lineStation = lineStationRepository.findByStation(station);
        lineStation.setLine(null);
        lineStationRepository.flush();
        assertThat(lineStation.getLine()).isNull();
    }
}
