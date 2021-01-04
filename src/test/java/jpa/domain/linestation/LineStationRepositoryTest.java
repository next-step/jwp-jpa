package jpa.domain.linestation;

import jpa.domain.line.Line;
import jpa.domain.line.LineRepository;
import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DataJpaTest
public class LineStationRepositoryTest {
    @Autowired
    LineRepository lines;

    @Autowired
    StationRepository stations;

    @Autowired
    LineStationRepository lineStations;

    @Test
    void save() {
        saveLineStation("2호선", "천호역");
    }

    private LineStation saveLineStation(String lineName, String stationName) {
        Line line = lines.save(new Line("2호선"));
        Station station = stations.save(new Station("천호역"));
        LineStation lineStation = lineStations.save(new LineStation(line,station));
        lineStations.flush();
        return lineStation;
    }

    @DisplayName("필수값 누락")
    @Test
    void validate() {
        Line line = lines.save(new Line("2호선"));

        assertThatIllegalArgumentException().isThrownBy(() -> {
            LineStation lineStation = lineStations.save(new LineStation(line, null));
        })
                .withMessageContaining("필수값 누락입니다.");

    }

    @DisplayName("삭제")
    @Test
    void delete() {
        LineStation lineStation = saveLineStation("2호선", "천호역");
        lineStations.delete(lineStation);
        lineStations.flush();
    }

    @DisplayName("업데이트")
    @Test
    void update() {
        // given
        LineStation lineStation = saveLineStation("5호선", "군자역");
        Station preStation = stations.save(new Station("아차산역"));

        // when
        lineStation.change(preStation);
        lineStation.change(10);
        lineStations.flush();

        // then
        assertThat(lineStations.findByPreStation(preStation)).isEqualTo(lineStation);
    }


    @Test
    @DisplayName("노선에 역을 추가할 때 이전 역과 얼마나 차이가 나는지 길이 등록")
    void addLineStationWithPreStationAndDistance() {
        LineStation expected = saveLineStation("2호선","잠실역", "삼성역", 5);
        assertThat(lineStations.findByPreStation(stations.findByName("삼성역"))).isEqualTo(expected);
    }

    private LineStation saveLineStation(String lineName, String stationName, String preStationName, int distance) {
        Line line = lines.save(new Line(lineName));
        Station station = stations.save(new Station(stationName));
        Station preStation = stations.save(new Station(preStationName));

        LineStation lineStation = new LineStation(line, station, preStation, distance);
        lineStations.save(lineStation);
        return  lineStation;
    }

    @DisplayName("거리 유효성 검증")
    @Test
    void validateDistance() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            saveLineStation("2호선", "잠실역", "잠실새내역", -3);
        })
                .withMessageContaining("거리는 0보다 커야합니다.");
    }

}
