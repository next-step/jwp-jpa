package jpa.repository;

import jpa.entity.Line;
import jpa.entity.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineRepository lineRepository;

    @BeforeEach
    void setup() {
        Line line2 = lineRepository.save(Line.builder().name("2호선").color("green").build());
        Line line9 = lineRepository.save(Line.builder().name("9호선").color("gold").build());

        stationRepository.save(Station.builder().name("당산역").lines(Arrays.asList(line2, line9)).build());
        stationRepository.save(Station.builder().name("잠실역").lines(Collections.singletonList(line2)).build());
        stationRepository.save(Station.builder().name("시청역").lines(Collections.singletonList(line2)).build());
        stationRepository.save(Station.builder().name("염창역").lines(Collections.singletonList(line9)).build());
        stationRepository.save(Station.builder().name("등촌역").lines(Collections.singletonList(line9)).build());
    }

    @Test
    void findByName() {
        // given
        String expected = "당산역";

        // when
        Station actual = stationRepository.findByName(expected);

        // then
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getLineStations().size()).isEqualTo(2)
        );
    }

    @Test
    void update() {
        // given
        Station station = stationRepository.findByName("당산역");
        
        // when
        station.changeStation("합정역");
        
        Station updateStation = stationRepository.findByName("합정역");
        Station deleteStation = stationRepository.findByName("당산역");
        
        // then
        assertAll(
                () -> assertThat(updateStation.getName()).isEqualTo("합정역"),
                () -> assertThat(deleteStation).isNull()
        );
    }

    @Test
    @DisplayName("역 추가")
    void save() {
        // given
        Line line = lineRepository.findByName("2호선");

        Station expected = Station.builder()
                .name("강남역")
                .lines(Collections.singletonList(line))
                .build();

        // when
        Station actual = stationRepository.save(expected);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("역 삭제")
    void delete() {
        // given
        Station expected = stationRepository.findByName("당산역");

        // when
        stationRepository.delete(expected);

        // then
        Station actual = stationRepository.findByName("당산역");
        assertThat(actual).isNull();
    }
}