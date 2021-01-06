package jpa.dao;

import jpa.domain.Favorite;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FavoriteRepositoryTest {
    @Autowired
    private FavoriteRepository favorites;

    @Test
    void save() {
        final Favorite expected = new Favorite();
        final Favorite actual = favorites.save(expected);
        assertThat(expected == actual).isTrue();
    }

    @Test
    void findById() {
        final Favorite expected = new Favorite();
        favorites.save(expected);
        final Optional<Favorite> actual = favorites.findById(expected.getId());
        assertThat(actual.get().getId()).isEqualTo(expected.getId());
    }

    @Test
    void update() {
        final Favorite expected = new Favorite();
        ZoneId zone = ZoneId.of("Asia/Seoul");
        expected.chageDate(zone);
        final Favorite actual = favorites.save(expected);
        assertThat(actual.getModified_date()).isNotEqualTo(actual.getCreated_date());
    }

    @Test
    void delete() {
        final Favorite expected = new Favorite();
        favorites.save(expected);
        favorites.deleteById(expected.getId());
        final List<Favorite> actual = favorites.findAll();
        assertThat(actual).isEmpty();
    }
}
