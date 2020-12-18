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

    private final Favorite testFavorite = new Favorite();

    @DisplayName("엔티티 저장 후 ID 부여 및 생성 일자 확인")
    @Test
    void saveTest() {
        Favorite saved = favoriteRepository.save(testFavorite);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedDate()).isNotNull();
    }

    @DisplayName("쿼리 메서드를 통한 조회 기능 확인")
    @Test
    void getTest() {
        int expectedSize = 1;
        favoriteRepository.save(testFavorite);

        assertThat(favoriteRepository.findAll()).hasSize(expectedSize);
    }

    @DisplayName("삭제 기능 확인")
    @Test
    void deleteTest() {
        Favorite saved = favoriteRepository.save(testFavorite);
        Long savedId = saved.getId();

        favoriteRepository.deleteById(savedId);

        assertThat(favoriteRepository.findById(savedId).isPresent()).isFalse();
    }
}
