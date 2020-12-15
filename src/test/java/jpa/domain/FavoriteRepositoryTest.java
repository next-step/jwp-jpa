package jpa.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @author : leesangbae
 * @project : jpa
 * @since : 2020-12-15
 */
@DataJpaTest
@DisplayName("Favorite Repository Test Class")
class FavoriteRepositoryTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Test
    @DisplayName("Favorite 저장하기 Test")
    void insertTest() {
        Favorite favoriteA = new Favorite();
        Favorite favoriteB = new Favorite();

        Favorite savedA = favoriteRepository.save(favoriteA);
        Favorite savedB = favoriteRepository.save(favoriteB);

        assertThat(savedA.getId()).isEqualTo(1);
        assertThat(savedB.getId()).isEqualTo(2);
    }

}
