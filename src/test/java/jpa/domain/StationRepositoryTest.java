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

    private final Station testStation = new Station("비 내리는 호남선");

    @DisplayName("엔티티 저장 후 ID, 생성일 부여 확인")
    @Test
    void saveTest() {
        Station saved = stationRepository.save(testStation);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedDate()).isNotNull();
    }

    @DisplayName("더티 체킹을 통한 업데이트 확인")
    @Test
    void updateTest() {
        assertThat(testStation.getModifiedDate()).isNull();
        Station changedStation = new Station("갱남역");
        testStation.updateStation(changedStation);
        assertThat(testStation.getModifiedDate()).isNotNull();
    }

    @DisplayName("쿼리 메서드를 통한 조회 기능 확인")
    @Test
    void getTest() {
        int expectedSize = 1;
        stationRepository.save(testStation);

        assertThat(stationRepository.findAll()).hasSize(expectedSize);
    }

    @DisplayName("삭제 기능 확인")
    @Test
    void deleteTest() {
        Station saved = stationRepository.save(testStation);
        Long savedId = saved.getId();

        stationRepository.deleteById(savedId);

        assertThat(stationRepository.findById(savedId).isPresent()).isFalse();
    }
}
