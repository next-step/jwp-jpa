package jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FavoriteRepositoryTest {
    @Autowired
    private FavoriteRepository favorites;
    private Favorite favorite;

    @BeforeEach
    void beforeEach() {
        favorite = new Favorite();
    }

    @DisplayName("`Favorite` 객체 생성")
    @Test
    void save() {
        // When
        Favorite actual = favorites.save(favorite);
        // Then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertSame(actual, favorite)
        );
    }

    @DisplayName("트랜잭션 안에서 `Favorite` 객체 삭제")
    @Test
    void delete() {
        // When
        favorites.delete(favorite);
        // Then
        assertNotNull(favorite);
    }
}
