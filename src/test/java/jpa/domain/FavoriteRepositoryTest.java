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

    @DisplayName("Member 엔티티 관련 기본 CRUD 테스트")
    @Test
    void simpleTest() {
        // 새로운 엔티티 생성 및 영속화
        Favorite favorite = new Favorite();
        favoriteRepository.save(favorite);

        // 영속화 시 자동으로 ID 부여
        assertThat(favorite.getId()).isNotNull();

        // 영속화 된 엔티티의 더티 체킹
        assertThat(favorite.getModifiedDate()).isNull();
        Favorite changedFavorite = new Favorite();
        favorite.updateFavorite(changedFavorite);
        assertThat(favorite.getModifiedDate()).isNotNull();

        // 대상 삭제 후 조회 불가능
        favoriteRepository.deleteById(favorite.getId());
        assertThat(favoriteRepository.findById(favorite.getId()).isPresent()).isFalse();
    }
}
