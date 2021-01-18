package jpa.repositories;

import jpa.domain.Line;
import jpa.domain.Station;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.either;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void setUp() {
        stationRepository.save(Station.of("몽촌토성역"));
    }

    @Test
    @DisplayName("정류장 추가")
    void save() {
        // given, when
         Station actual = stationRepository.save(Station.of("잠실역"));

        // then
        StationAssertAll(actual, "잠실역");
    }

    @Test
    @DisplayName("정류장 수정")
    void update() {
        // given
        String changeName = "의정부역";

        // when
        Station Station = stationRepository.findByName("몽촌토성역");
        Station.setName(changeName);
        Station actual = stationRepository.save(Station);

        // then
        assertThat(actual.getName()).isEqualTo("의정부역");
    }

    @Test
    @DisplayName("name unique 속성 체크")
    void uniqueCheck() {
        // given when then
        assertThrows(DataIntegrityViolationException.class, () ->{
            stationRepository.save(Station.of("몽촌토성역"));
        });
    }

    @Test
    @DisplayName("정류장 삭제")
    void delete() {
        // given when
        stationRepository.delete(stationRepository.findByName("몽촌토성역"));
        Station actual = stationRepository.findByName("몽촌토성역");

        // then
        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("정류장 조회")
    void select() {
        // given when
        Station actual = stationRepository.findByName("몽촌토성역");

        // then
        StationAssertAll(actual, "몽촌토성역");
    }

    private void StationAssertAll(Station actual,String expectedName) {
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expectedName),
                () -> assertThat(actual.getCreatedDate()).isNotNull(),
                () -> assertThat(actual.getModifiedDate()).isNotNull()
        );
    }

    @Test
    @DisplayName("지하철역 조회 시 속한 노선 개수 및 이름 확인")
    void selectRelationMappingStationAndLine() {
        saves();
        Station station_expressBusTerminal = stationRepository.findByName("고속터미널");

        assertThat(stationRepository).isNotNull();
        assertThat(station_expressBusTerminal.getLines()).hasSize(3);
        station_expressBusTerminal.getLines().forEach(line -> {
            MatcherAssert.assertThat(line.getName(),
                    either(containsString("7호선"))
                            .or(containsString("9호선"))
                            .or(containsString("3호선")));
        });
    }

    private void saves() {
        Station station_expressBusTerminal = stationRepository.save(new Station("고속터미널"));

        station_expressBusTerminal.addLine(new Line("7호선", "올리브색"));
        station_expressBusTerminal.addLine(new Line("9호선", "골드색"));
        station_expressBusTerminal.addLine(new Line("3호선", "주황색"));

        stationRepository.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("중복 된 지하철역 이름 체크")
    void validationOverlapLineName() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            stationRepository.save(new Station("고속터미널"));
            stationRepository.save(new Station("고속터미널"));
        });
    }
}