package jpa.domain.favorite;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class FavoriteRepositoryTest{
    @Autowired
    FavoriteRepository favorites;

    @Test
    void save() {
        favorites.save(new Favorite());
    }
}
