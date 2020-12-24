package jpa.repository;

import jpa.domain.Favorite;
import jpa.domain.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class FavoriteRepositoryTest {
    @Autowired
    private FavoriteRepository favorites;

    @Test
    void save() {
        Favorite expected = new Favorite();
        Favorite actual = favorites.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull()
        );
    }

    @Test
    void findById() {
        favorites.save(new Favorite());
        assertThat(favorites.findById(1L)).isNotNull();
    }
}
