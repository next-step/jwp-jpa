package jpa.repository;

import jpa.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Test
    @DisplayName("지하철역 저장 테스트")
    void save() {
        // given
        Station expected = new Station("잠실역");

        // when
        Station actual = stationRepository.save(expected);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    @DisplayName("지하철역 이름으로 조회 테스트")
    void findByName() {
        // given
        String expected = "잠실역";
        stationRepository.save(new Station(expected));

        // when
        String actual = stationRepository.findByName(expected).getName();

        // then
        assertThat(actual).isEqualTo(expected);

        // 중복된 이름 insert 시 exception 발생 테스트
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> {
            stationRepository.save(new Station(expected));
        });
    }

    @Test
    @DisplayName("동일성 보장 테스트름 - 아이디")
    void identity() {
        // given
        Station station1 = stationRepository.save(new Station("잠실역"));

        // when
        Station station2 = stationRepository.findById(station1.getId()).orElse(null);

        // then
        assertThat(station1 == station2).isTrue();
    }

    @Test
    @DisplayName("동일성 보장 테스트 - 이름")
    void identityByName() {
        // given
        Station station1 = stationRepository.save(new Station("잠실역"));

        // when
        Station station2 = stationRepository.findByName(station1.getName());

        // then
        assertThat(station1 == station2).isTrue();
    }

    @Test
    @DisplayName("변경 감지 테스트")
    void update() {
        // given
        Station station1 = stationRepository.save(new Station("잠실역"));

        // when
        station1.setName("강남역");
        Station station2 = stationRepository.findByName("강남역");

        // then
        assertThat(station1 == station2).isTrue();
    }

    @Test
    @DisplayName("지하철역 삭제 테스트")
    void delete() {
        // given
        Station expected = stationRepository.save(new Station("잠실역"));

        // when
        stationRepository.delete(expected);
        Station actual = stationRepository.findByName("잠실역");

        // then
        assertThat(actual).isNull();
    }

}