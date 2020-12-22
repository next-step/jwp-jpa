package jpa.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class FavoriteTest {

    @Autowired
    private FavoriteRepository favorites;

    @Test
    void save() {
        Favorite expected = new Favorite();
        Favorite actual = favorites.save(expected);
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void read() {
        Favorite actual = favorites.save(new Favorite());
        Long id = actual.getId();
        Favorite favorite = favorites.findById(id).get();
        assertThat(actual).isEqualTo(favorite);
    }

    @Test
    void update() {
        Favorite actual = favorites.save(new Favorite());
        LocalDateTime now = LocalDateTime.now();
        actual.update();
        Long id = actual.getId();
        Favorite favorite = favorites.findById(id).get();
        assertThat(favorite.getModifiedDate()).isAfter(now);
    }

    @Test
    void delete() {
        Favorite actual = favorites.save(new Favorite());
        Long id = actual.getId();
        favorites.delete(actual);
        Optional<Favorite> byId = favorites.findById(id);
        assertThat(byId).isNotPresent();
    }


}
