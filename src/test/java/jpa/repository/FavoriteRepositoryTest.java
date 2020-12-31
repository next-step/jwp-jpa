package jpa.repository;

import jpa.domain.Favorite;
import jpa.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FavoriteRepositoryTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Test
    void create() {
        Favorite favorite = favoriteRepository.save(new Favorite());
        assertThat(favorite.getId()).isNotNull();
    }

    @Test
    void findById() {
        Favorite favorite1 = favoriteRepository.save(new Favorite());
        Favorite favorite2 = favoriteRepository.findById(favorite1.getId()).orElse(null);
        assertThat(favorite1 == favorite2).isTrue();
    }

    @Test
    void delete() {
        Favorite favorite1 = favoriteRepository.save(new Favorite());
        favoriteRepository.delete(favorite1);
        favoriteRepository.flush();
        Favorite favorite2 = favoriteRepository.findById(favorite1.getId()).orElse(null);
        assertThat(favorite2).isNull();
    }
}