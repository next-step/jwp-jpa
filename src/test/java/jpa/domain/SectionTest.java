package jpa.domain;

import jpa.repository.LineRepository;
import jpa.repository.StationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class SectionTest {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineRepository lineRepository;

    @Test
    @DisplayName("노선에 역을 추가할 때는 이전 역과 얼마나 차이가 나는지 길이(distance)를 알고 있어야 한다.")
    void save() {
        // given
        Line line = lineRepository.save(new Line("2호선"));
        Station prevStation = stationRepository.save(new Station("잠실역"));
        Station station = stationRepository.save(new Station("교대역"));
        double expected = 3;

        // when
        Section section = new Section(prevStation, station, expected);
        line.addStation(section);

        // then
        double actual = lineRepository.save(line).getDistanceByStation(station);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("이전역이 없을 경우 테스트")
    public void notExistPrevStation() {
        // given
        Line line = lineRepository.save(new Line("2호선"));
        //Station prevStation = stationRepository.save(new Station("잠실역"));
        Station station = stationRepository.save(new Station("교대역"));
        double expected = 3;

        // when/then
        assertThatThrownBy(() -> new Section(stationRepository.findByName("잠실역"), station, expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("저장하지 않은 지하철역으로 조회하기")
    public void getDistanceByNotMatchedStation() {
        // given
        Line line = lineRepository.save(new Line("2호선"));

        // when/then
        Station station = stationRepository.save(new Station("강남역"));
        assertThatThrownBy(() -> lineRepository.save(line).getDistanceByStation(station))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
