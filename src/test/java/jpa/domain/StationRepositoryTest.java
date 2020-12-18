package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StationRepositoryTest {

    @Autowired
    StationRepository stations;

    @Test
    @DisplayName("역 저장 테스트")
    public void save() throws Exception {
        Station expected = new Station("화정역");
        Station actual = stations.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    @DisplayName("역 id로 조회 테스트")
    public void findById() throws Exception {
        Station expected = new Station("화정역");
        stations.save(expected);
        Station byId = stations.findById(expected.getId()).get();

        assertAll(
                () -> assertThat(byId.getId()).isNotNull(),
                () -> assertThat(byId.getId()).isEqualTo(expected.getId()),
                () -> assertThat(byId.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    @DisplayName("역 name 으로 조회 테스트")
    public void findByName() throws Exception {
        Station expected = new Station("화정역");
        stations.save(expected);
        Station byName = stations.findByName("화정역");

        assertAll(
                () -> assertThat(byName.getId()).isNotNull(),
                () -> assertThat(byName.getId()).isEqualTo(expected.getId()),
                () -> assertThat(byName.getName()).isEqualTo(expected.getName())
        );
    }
}
