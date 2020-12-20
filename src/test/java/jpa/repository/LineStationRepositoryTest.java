package jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import jpa.domain.Line;
import jpa.domain.LineStation;
import jpa.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class LineStationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private LineStationRepository lineStationRepository;

    @DisplayName("노선조회 - 지하철역 목록")
    @Test
    void line() {
        //given
        Line line = lineRepository.save(new Line("2호선"));
        List<Station> stations = stationRepository
              .saveAll(Arrays.asList(new Station("강남역"), new Station("역삼역"), new Station("삼성역")));
        List<LineStation> lineStations = stations.stream()
              .map(station -> new LineStation(station, line))
              .collect(Collectors.toList());

        //when
        List<LineStation> actual = lineStationRepository.saveAll(lineStations);

        //then
        List<LineStation> byLine = lineStationRepository.findByLine(line);
        assertThat(actual).isEqualTo(byLine);
    }

    @DisplayName("지하철역 조회 - 환승역")
    @Test
    void station() {
        //given
        Line line2 = lineRepository.save(new Line("2호선"));
        Line lineB = lineRepository.save(new Line("분당선"));
        Station station = stationRepository.save(new Station("선릉역"));

        //when
        List<LineStation> actual = lineStationRepository.saveAll(
              Arrays.asList(new LineStation(station, line2), new LineStation(station, lineB)));

        //then
        List<LineStation> byStation = lineStationRepository.findByStation(station);
        assertThat(actual).isEqualTo(byStation);
    }
}