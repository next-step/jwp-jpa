package jpa.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class FavoriteRepositoryTest {
	@Autowired
	private FavoriteRepository favoriteRepository;

	@Test
	void saveTest() {
		Favorite expected = new Favorite();
		Favorite actual = favoriteRepository.save(expected);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull()
		);
	}

	@Test
	void createdAndModifiedTimeNotNullTest() {
		Favorite favorite = favoriteRepository.save(new Favorite());

		assertThat(favorite.getCreatedDate()).isNotNull();
		assertThat(favorite.getModifiedDate()).isNotNull();
	}

	@Test
	void deleteTest() {
		Favorite expected = new Favorite();
		Favorite actual = favoriteRepository.save(expected);
		assertThat(actual.getId()).isEqualTo(expected.getId());

		favoriteRepository.delete(actual);
		assertAll(
			() -> assertThat(favoriteRepository.findById(actual.getId())).isEmpty(),
			() -> assertThat(favoriteRepository.findAll().size()).isEqualTo(0)
		);
	}
}