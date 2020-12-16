package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class FavoriteRepositoryTest {

    @Autowired
    private FavoriteRepository favorites;

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
    @Rollback(value = false)
    void save() {
        // given
        Favorite expected = new Favorite();

        // when
        Favorite actual = favorites.save(expected);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual).isEqualTo(expected),
                () -> assertThat(actual).isSameAs(expected)
        );
    }
}
