package jpa.domain;

import jpa.common.JpaAuditingDate;
import jpa.config.JpaAuditingConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE
        , classes = {JpaAuditingConfig.class, JpaAuditingDate.class}
))
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
        assertAll(
                () -> assertThat(saveFavorite.getId()).isNotNull(),
                () -> assertEquals(saveFavorite, favorite),
                () -> assertThat(saveFavorite.getCreatedDate()).isNotNull()
        );
    }

    @Test
    @DisplayName("아이디로 favorite 조회 테스트")
    public void findById() {
        // given
        Favorite favorite = favoriteRepository.save(new Favorite());
        Long favoriteId = favorite.getId();

        // when
        Favorite favoriteFound = this.favoriteRepository.findById(favoriteId).get();

        // then
        assertThat(favoriteFound.getId()).isEqualTo(favoriteId);
    }
}
