package jpa.repositories;

import jpa.domain.Favorite;
import jpa.domain.repositories.FavoriteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class FavoriteRepositoryTest {

  @Autowired
  private FavoriteRepository favorites;

  @Test
  void save() {
    final Favorite expected = getFavoriteSampleData();
    assertThat(expected.getId()).isNull();

    final Favorite actual = favorites.save(expected);
    assertAll(
        () -> assertThat(actual.getId()).isNotNull(),
        () -> assertThat(actual.getCreatedDate()).isNotNull(),
        () -> assertThat(actual.getModifiedDate()).isNotNull()
    );
  }

  @Test
  void delete() {
    final Favorite favorite = favorites.save(getFavoriteSampleData());
    favorites.delete(favorite);
    assertThat(favorites.findById(favorite.getId())).isNotPresent();
  }

  private Favorite getFavoriteSampleData() {
    return new Favorite();
  }
}