package jpa.domain;

import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("favorite 저장 테스트")
    public void save() {
        // given
        Favorite favorite = new Favorite();

        // when
        Favorite saveFavorite = this.favoriteRepository.save(favorite);

        // then
        assertThat(saveFavorite).isEqualTo(favorite);
    }

    @Test
    @DisplayName("이름으로 favorite 조회 테스트")
    public void findById() {
        // given
        Favorite favorite = favoriteRepository.save(new Favorite());
        Long favoriteId = favorite.getId();

        // when
        Optional<Favorite> favoriteFindById = this.favoriteRepository.findById(favoriteId);

        // then
        assertThat(favoriteFindById.get().getId()).isEqualTo(favoriteId);
    }
}
