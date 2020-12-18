package jpa.repository;

import jpa.entity.Favorite;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class FavoriteRepositoryTest {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Test
    void save() {
        Favorite favorite1 = new Favorite();
        Favorite result = favoriteRepository.save(favorite1);
        assertAll(
                () -> assertThat(result.getId()).isNotNull()
        );
    }

    @Test
    void findById() {
        Favorite favorite1 = favoriteRepository.save(new Favorite());
        Favorite favorite2 = favoriteRepository.findById(favorite1.getId()).get();
        assertThat(favorite1 == favorite2).isTrue();
    }
}
