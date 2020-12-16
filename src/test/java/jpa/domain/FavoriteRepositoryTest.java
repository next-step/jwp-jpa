package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FavoriteRepositoryTest {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @DisplayName("엔티티 저장 후 ID 부여 확인")
    @Test
    void saveTest() {
        Favorite favorite = new Favorite();
        favoriteRepository.save(favorite);

        assertThat(favorite.getId()).isNotNull();
    }

    @DisplayName("더티 체킹을 통한 업데이트 확인")
    @Test
    void updateTest() {
        Favorite favorite = new Favorite();

        assertThat(favorite.getModifiedDate()).isNull();
        Favorite changedFavorite = new Favorite();
        favorite.updateFavorite(changedFavorite);
        assertThat(favorite.getModifiedDate()).isNotNull();
    }

    @DisplayName("쿼리 메서드를 통한 조회 기능 확인")
    @Test
    void getTest() {
        int expectedSize = 1;
        Favorite favorite = new Favorite();
        favoriteRepository.save(favorite);

        assertThat(favoriteRepository.findAll()).hasSize(expectedSize);
    }

    @DisplayName("삭제 기능 확인")
    @Test
    void deleteTest() {
        Favorite favorite = new Favorite();
        Favorite saved = favoriteRepository.save(favorite);
        Long savedId = saved.getId();

        favoriteRepository.deleteById(savedId);

        assertThat(favoriteRepository.findById(savedId).isPresent()).isFalse();
    }
}
