package jpa.domain;

import jpa.repository.FavoriteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FavoriteRepositoryTest {

    @Autowired
    FavoriteRepository favoriteRepository;

    @Test
    void create() {
        Favorite expected = new Favorite();
        Favorite actual = favoriteRepository.save(expected);
        favoriteRepository.flush();

        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void delete() {
        Favorite expected = new Favorite();
        favoriteRepository.save(expected);

        Optional<Favorite> deleteFavorite = favoriteRepository.findById(1L);
        deleteFavorite.ifPresent(favorite -> favoriteRepository.delete(favorite));
        favoriteRepository.flush();

        assertThat(deleteFavorite.isPresent()).isTrue();
    }
}
