package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class StationRepositoryTest {

    @Autowired
    StationRepository stationRepository;

    @Test
    @DisplayName("station 저장 테스트")
    public void save() {
        // given
        Station station = new Station("산본역");

        // when
        Station saveStation = this.stationRepository.save(station);

        // then
        assertAll(
                () -> assertThat(saveStation.getId()).isNotNull(),
                () -> assertEquals(saveStation, station)
        );
    }

    @Test
    @DisplayName("이름으로 station 조회 테스트")
    public void findByName() {
        // given
        String name = "산본역";
        stationRepository.save(new Station(name));

        // when
        Station stationFindByName = this.stationRepository.findByName(name);

        // then
        assertThat(stationFindByName.getName()).isEqualTo(name);
    }
}
