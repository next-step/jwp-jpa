package jpa.dao;

import jpa.repository.FavoriteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FavoriteTest {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Test
    void findById() {
        Favorite favorite = new Favorite();
        Favorite source = favoriteRepository.save(favorite);
        Favorite expected = favoriteRepository.findById(source.getId())
                .orElseGet(Favorite::new);
        assertThat(source.getId()).isEqualTo(expected.getId());
    }
}
