package jpa.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @author : leesangbae
 * @project : jpa
 * @since : 2020-12-15
 */
@DataJpaTest
@DisplayName("Favorite Repository Test Class")
class FavoriteRepositoryTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private StationRepository stationRepository;

    @Test
    @DisplayName("Favorite 저장하기 Test")
    void insertTest() {
        Favorite favoriteA = new Favorite();
        Favorite favoriteB = new Favorite();

        Favorite savedA = favoriteRepository.save(favoriteA);
        Favorite savedB = favoriteRepository.save(favoriteB);

        assertThat(savedA.getId()).isEqualTo(1);
        assertThat(savedB.getId()).isEqualTo(2);
    }

    @SuppressWarnings("NonAsciiCharacters")
    @Test
    @DisplayName("Favorite 시작역, 종착역 저장 Test")
    void insertWithStationTest() {

        Station 서울대입구역 = stationRepository.save(new Station("서울대입구역"));
        Station 잠실역 = stationRepository.save(new Station("잠실역"));

        favoriteRepository.save(new Favorite(서울대입구역, 잠실역));

        Favorite savedFavorite = favoriteRepository.findById(1L).get();

        assertThat(savedFavorite.getSourceStation()).isEqualTo(서울대입구역);
        assertThat(savedFavorite.getDestinyStation()).isEqualTo(잠실역);
    }
}
