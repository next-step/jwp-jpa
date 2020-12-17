package jpa.repository;

import jpa.domain.Favorite;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FavoriteRepositoryTest {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Test
    void save() {
        Favorite expected = new Favorite();
        Favorite actual = favoriteRepository.save(expected);
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void findByAll() {
        favoriteRepository.save(new Favorite());
        List<Favorite> favoriteList = favoriteRepository.findAll();
        assertThat(favoriteList.size()).isEqualTo(1);
    }
}
