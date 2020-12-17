package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class FavoriteRepositoryTest {

    @Autowired
    private FavoriteRepository favorites;

    @Autowired
    private EntityManager em;

    @DisplayName("조회 시 값이 없을 경우 null 이 반환됩니다.")
    @Test
    void findOneNull() {
        // when
        Optional<Favorite> favoriteOptional = favorites.findById(10L);

        // then
        assertThat(favoriteOptional.isPresent()).isFalse();
    }

    @DisplayName("여러건 조회를 확인합니다.")
    @Test
    void findAll() {
        // given
        Favorite favorite1 = new Favorite();
        Favorite favorite2 = new Favorite();
        Favorite favorite3 = new Favorite();
        favorites.saveAll(Arrays.asList(favorite1, favorite2, favorite3));

        // when
        List<Favorite> favoriteList = favorites.findAll();

        // then
        assertAll(
                () -> assertThat(favoriteList).isNotNull(),
                () -> assertThat(favoriteList).containsExactly(favorite1, favorite2, favorite3)
        );
    }

    @DisplayName("정상적으로 저장되는지 확인합니다.")
    @Test
    void save() {
        // given
        Favorite expected = new Favorite();

        // when
        Favorite actual = favorites.save(expected);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getCreatedDate()).isNotNull(),
                () -> assertThat(actual).isEqualTo(expected),
                () -> assertThat(actual).isSameAs(expected)
        );
    }

    @DisplayName("즐겨찾기에는 출발역과 도착역이 포함되어 있다")
    @Test
    void containsStation() {
        // given
        // 지하철 역 저장
        Station station1 = new Station("잠실역");
        Station station2 = new Station("합정역");
        em.persist(station1);
        em.persist(station2);

        // 즐겨찾기 저장
        String name = "1";
        Favorite favorite = new Favorite(name);
        favorite.addStartStation(station1);
        favorite.addEndStation(station2);
        em.persist(favorite);

        // when
        Favorite expected = favorites.findByName(name).get();

        // then
        assertAll(
                () -> assertThat(expected.getStartStation()).isEqualTo(station1),
                () -> assertThat(expected.getEndStation()).isEqualTo(station2)
        );
    }
}
