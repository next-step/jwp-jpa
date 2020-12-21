package jpa.com.jaenyeong.domain.station;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Station Repository 테스트")
class StationRepositoryTest {
    @Autowired
    private StationRepository stations;

    private Station gangnam;
    private Station jamsil;
    private Station kkachisan;

    @BeforeEach
    void setUp() {
        gangnam = new Station("강남역");
        jamsil = new Station("잠실역");
        kkachisan = new Station("까치산역");
    }

    @Test
    @DisplayName("객체 생성시 아이디 존재 여부 테스트")
    void create() {
        assertAll(
            () -> assertThat(gangnam.getId()).isNull(),
            () -> assertThat(jamsil.getId()).isNull(),
            () -> assertThat(kkachisan.getId()).isNull()
        );
    }

    @Test
    @DisplayName("객체 저장 테스트")
    void save() {
        assertThat(gangnam.getId()).isNull();
        assertThat(gangnam.getCreatedDate()).isNull();
        assertThat(gangnam.getModifiedDate()).isNull();

        final Station gangnamStation = stations.save(gangnam);

        assertThat(gangnamStation).isNotNull();
        assertThat(gangnamStation.getId()).isNotNull();
        assertThat(gangnamStation.getCreatedDate()).isNotNull();
        assertThat(gangnamStation.getModifiedDate()).isNotNull();
    }

    @Test
    @DisplayName("객체를 이름으로 찾는 테스트")
    void findByName() {
        final Station gangnamStation = stations.findByName("강남역");
        final Station jamsilStation = stations.findByName(stations.save(jamsil).getName());

        assertThat(gangnamStation).isNull();
        assertThat(jamsilStation).isNotNull();
    }

    @Test
    @DisplayName("전체 조회 테스트")
    void findAll() {
        stations.save(gangnam);
        stations.save(jamsil);
        stations.save(kkachisan);

        assertSame(stations.findAll().size(), 3);
    }

    @Test
    @DisplayName("객체 수정 테스트")
    void update() {
        final Station savedGangnam = stations.save(gangnam);
        assertSame(savedGangnam.getName(), "강남역");

        savedGangnam.changeStationName("용산역");
        final Station foundYongsan = stations.findByName("용산역");

        assertNotEquals(foundYongsan.getName(), "강남역");
    }

    @Test
    @DisplayName("객체 삭제 테스트")
    void delete() {
        stations.save(gangnam);
        stations.save(jamsil);
        stations.save(kkachisan);

        stations.deleteById(gangnam.getId());
        assertSame(stations.findAll().size(), 2);

        stations.deleteById(jamsil.getId());
        stations.deleteById(kkachisan.getId());
        assertSame(stations.findAll().size(), 0);
    }
}
