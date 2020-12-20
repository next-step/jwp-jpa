package jpa.domain.station;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        stationRepository.save(new Station("판교역"));
        stationRepository.save(new Station("강남역"));
    }

    @Test
    @DisplayName("Station 추가 테스트")
    void insert_station_test() {
        // given
        Station station = new Station("잠실역");

        // when
        Station persistStation = stationRepository.save(station);

        // then
        assertThat(persistStation.getId()).isNotNull();
        assertThat(persistStation.getCreatedDate()).isNotNull();
        assertThat(persistStation.getModifiedDate()).isNotNull();
    }

    @Test
    @DisplayName("Station 전체 조회 테스트")
    void select_all_station_test() {
        // given
        Station station = new Station("잠실역");
        stationRepository.save(station);

        // when
        List<Station> stations = stationRepository.findAll();
        List<String> stationNames = stations.stream()
                .map(Station::getName)
                .collect(Collectors.toList());

        // then
        assertAll(
                () -> assertThat(stationNames.size()).isEqualTo(3),
                () -> assertThat(stationNames).contains("판교역", "잠실역", "강남역")
        );
    }

    @Test
    @DisplayName("Station 조회 테스트")
    void select_station_test() {
        // given
        Station station = new Station("잠실역");

        // when
        Station persistStation = stationRepository.save(station);

        // then
        assertThat(persistStation.getName()).isEqualTo("잠실역");
    }

    @Test
    @DisplayName("Station 수정 테스트")
    void update_station_test() {
        // given
        String changeStation = "이대역";

        // when
        Station station = stationRepository.findByName("강남역");
        station.updateName(changeStation);

        Station updatedStation = stationRepository.findByName(changeStation);

        // then
        assertThat(updatedStation.getName()).isEqualTo(changeStation);
    }

    @Test
    @DisplayName("Station 삭제 테스트")
    void delete_station_test() {
        // given
        Station station = stationRepository.findByName("강남역");

        // when
        stationRepository.delete(station);

        // then
        Station deletedStation = stationRepository.findByName("강남역");
        assertThat(deletedStation).isNull();
    }
}
