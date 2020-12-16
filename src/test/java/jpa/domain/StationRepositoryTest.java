package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StationRepositoryTest {
    @Autowired
    private StationRepository stationRepository;

    @DisplayName("Station 엔티티 관련 기본 CRUD 테스트")
    @Test
    void simpleTest() {
        String stationName = "testStation";
        String modifiedName = "modified";

        // 새로운 엔티티 생성 및 영속화
        Station station = new Station(stationName);
        stationRepository.save(station);

        // 영속화 시 자동으로 ID 부여
        assertThat(station.getId()).isNotNull();

        // 영속화 된 엔티티의 더티 체킹
        assertThat(station.getModifiedDate()).isNull();
        Station changeStation = new Station(modifiedName);
        station.updateStation(changeStation);
        assertThat(station.getModifiedDate()).isNotNull();

        // 대상 삭제 후 조회 불가능
        stationRepository.deleteById(station.getId());
        assertThat(stationRepository.findById(station.getId()).isPresent()).isFalse();
    }
}
