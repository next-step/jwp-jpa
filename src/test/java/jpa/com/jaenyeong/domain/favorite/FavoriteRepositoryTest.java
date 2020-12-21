package jpa.com.jaenyeong.domain.favorite;

import jpa.com.jaenyeong.domain.station.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

@DataJpaTest
@DisplayName("Favorite Repository 테스트")
class FavoriteRepositoryTest {
    @Autowired
    private FavoriteRepository favorites;

    private Station kkachisan;
    private Station gangnam;
    private Favorite favorite;

    @BeforeEach
    void setUp() {
        kkachisan = new Station("까치산역");
        gangnam = new Station("강남역");

        favorite = new Favorite(kkachisan, gangnam);
    }

    @Test
    @DisplayName("객체 저장 테스트")
    void save() {
        final Favorite save = favorites.save(favorite);

        assertThat(save.getId()).isNotNull();
        assertThat(save.getCreatedDate()).isNotNull();
        assertThat(save.getModifiedDate()).isNotNull();
    }

    @Test
    @DisplayName("객체 조회 테스트")
    void findById() {
        final Favorite save = favorites.save(favorite);

        assertThat(favorites.findById(save.getId())).isNotNull();
    }

    @Test
    @DisplayName("객체 삭제 테스트")
    void delete() {
        final Favorite save = favorites.save(favorite);
        final Long targetId = save.getId();

        assertThat(favorites.findById(targetId)).isNotNull();

        favorites.deleteById(targetId);
        final Favorite afterDelete = favorites.findById(targetId).orElse(null);

        assertThat(afterDelete).isNull();
    }

    @Test
    @DisplayName("즐겨찾기의 출발역명과 도착역명 테스트")
    void departureAndArrivalStationsName() {
        assertSame(favorite.getDepartureStationName(), "까치산역");
        assertSame(favorite.getArrivalStationName(), "강남역");
    }
}
