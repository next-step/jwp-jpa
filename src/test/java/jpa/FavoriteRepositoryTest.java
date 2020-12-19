package jpa;

import jpa.domain.Favorite;
import jpa.domain.FavoriteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class FavoriteRepositoryTest {
    @Autowired
    private FavoriteRepository repository;

    @Test
    void save() {
        Favorite expected = new Favorite();
        Favorite actual = repository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull()
        );
    }

    @Test
    void findById() {
        Favorite expected = new Favorite();
        Favorite saved = repository.save(expected);
        Optional<Favorite> byId = repository.findById(saved.getId());
        assertThat(byId.get().getId()).isEqualTo(expected.getId());
    }
}