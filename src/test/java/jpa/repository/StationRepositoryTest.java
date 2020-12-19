package jpa.repository;

import jpa.domain.Station;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class StationRepositoryTest {
    @Autowired
    private StationRepository stationRepository;

    private Station saveStation(String name) {
        return stationRepository.save(new Station(name));
    }

    @Test
    @DisplayName("Station 저장 테스트")
    void stationSaveTest() {
        Station station = saveStation("수서");

        assertThat(station.getId()).isNotNull();
    }

    @Test
    @DisplayName("Station 조회 테스트")
    void stationSelectTest() {
        saveStation("왕십리");
        saveStation("선릉");
        saveStation("모란");
        saveStation("수원");
        saveStation("인천");

        Station findStation1 = stationRepository.findByName("왕십리");
        Station findStation2 = stationRepository.findByName("수원");

        assertThat(findStation1.getName()).isEqualTo("왕십리");
        assertThat(findStation2.getName()).isEqualTo("수원");

        List<Station> result = stationRepository.findAll();

        assertThat(result.size()).isEqualTo(5);
    }
}
