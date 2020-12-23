package jpa.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LineStationRepositoryTest {

    @Autowired
    LineStationRepository lineStationRepository;

    @Autowired
    LineRepository lineRepository;

    @Autowired
    StationRepository stationRepository;

    @Test
    @DisplayName("노선으로 속한 지하철역 조회")
    public void findByLine() throws Exception {
        // given
        Line savedLine = lineRepository.save(new Line("3호선", "주황"));
        Station station1 = new Station("화정역");
        Station station2 = new Station("고속터미널역");
        Station station3 = new Station("오금역");
        List<Station> stations = stationRepository.saveAll(Arrays.asList(station1, station2, station3));
        LineStation lineStation1 = new LineStation(savedLine, station1, station3, 2);
        LineStation lineStation2 = new LineStation(savedLine, station2, station1, 5);
        LineStation lineStation3 = new LineStation(savedLine, station3, station2, 10);
        List<LineStation> lineStations = lineStationRepository.saveAll(Arrays.asList(lineStation1, lineStation2, lineStation3));

        // when
        List<LineStation> byLine = lineStationRepository.findByLine(savedLine);

        // then
        assertThat(lineStations.size()).isEqualTo(stations.size());
        assertThat(lineStations).isEqualTo(byLine);
        assertAll(
                () -> lineStations.forEach(lineStation -> {
                    assertThat(stations.contains(lineStation.getStation())).isTrue();
                    assertThat(lineStation.getLine().equals(savedLine));
                })
        );
        assertAll(
                () -> byLine.forEach(lineStation -> {
                    assertThat(stations.contains(lineStation.getStation())).isTrue();
                    assertThat(lineStation.getLine().equals(savedLine));
                })
        );
    }

    @Test
    @DisplayName("지하철역으로 노선 조회")
    public void findByStation() throws Exception {
        // given
        Station oldStation = stationRepository.save(new Station("TEST_이전역"));
        Station savedStation = stationRepository.save(new Station("홍대입구"));
        Line line1 = new Line("2호선", "초록");
        Line line2 = new Line("경의중앙선", "에메랄드색");
        Line line3 = new Line("공항철도", "파란색");
        List<Line> lines = lineRepository.saveAll(Arrays.asList(line1, line2, line3));
        LineStation lineStation1 = new LineStation(line1, savedStation, oldStation, 2);
        LineStation lineStation2 = new LineStation(line2, savedStation, oldStation, 5);
        LineStation lineStation3 = new LineStation(line3, savedStation, oldStation, 10);
        List<LineStation> lineStations = lineStationRepository.saveAll(Arrays.asList(lineStation1, lineStation2, lineStation3));

        // when
        List<LineStation> byStation = lineStationRepository.findByStation(savedStation);

        // then
        assertThat(lineStations.size()).isEqualTo(lines.size());
        assertThat(lineStations).isEqualTo(byStation);
        assertAll(
                () -> lineStations.forEach(lineStation -> {
                    assertThat(lines.contains(lineStation.getLine())).isTrue();
                    assertThat(lineStation.getStation().equals(savedStation));
                })
        );
        assertAll(
                () -> byStation.forEach(lineStation -> {
                    assertThat(lines.contains(lineStation.getLine())).isTrue();
                    assertThat(lineStation.getStation().equals(savedStation));
                })
        );
    }
}
