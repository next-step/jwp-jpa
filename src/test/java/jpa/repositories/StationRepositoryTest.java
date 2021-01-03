package jpa.repositories;

import jpa.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

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

        // then
        Station actual = stationRepository.findByName("몽촌토성역");
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
}