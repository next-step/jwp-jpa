package jpa.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @author : leesangbae
 * @project : jpa
 * @since : 2020-12-17
 */
@DataJpaTest
@DisplayName("LineStation Repository Test Class")
class LineStationRepositoryTest {

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineStationRepository lineStationRepository;

    @SuppressWarnings("NonAsciiCharacters")
    @BeforeEach
    void initializeData() {
        Line lineNumber2 = lineRepository.save(new Line("2호선", "Green"));
        Line lineNumber4 = lineRepository.save(new Line("4호선", "Blue"));

        Station 서울대입구 = stationRepository.save(new Station("서울대입구"));
        Station 낙성대 = stationRepository.save(new Station("낙성대"));
        Station 사당 = stationRepository.save(new Station("사당"));

        Station 총신대입구 = stationRepository.save(new Station("총신대입구"));
        Station 남태령 = stationRepository.save(new Station("남태령"));

        lineStationRepository.save(new LineStation(lineNumber2, 서울대입구));
        lineStationRepository.save(new LineStation(lineNumber2, 낙성대));
        lineStationRepository.save(new LineStation(lineNumber2, 사당));

        lineStationRepository.save(new LineStation(lineNumber4, 총신대입구));
        lineStationRepository.save(new LineStation(lineNumber4, 사당));
        lineStationRepository.save(new LineStation(lineNumber4, 남태령));
    }

    @Test
    @DisplayName("하나의 역에 두가지 Line Test (환승역)")
    void lineStationSaveTest() {
        Station savedSadang = stationRepository.findByNameWithLineStation("사당");

        assertThat(savedSadang.getLineStations().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Line에 포함된 역 Test")
    void lineStationSelectLineTest() {
        Line savedLineNumber3 = lineRepository.findByNameWithLineStation("4호선");

        assertThat(savedLineNumber3.getLineStations().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Line에 포함된 역 Test")
    void shouldBeExceptionCreateFavoriteTest() {
        assertThatThrownBy(() -> new Line(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Line의 name, color는 필수 값 입니다.");
    }

}
