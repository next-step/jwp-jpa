package jpa.step1;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.step1.domain.Favorite;
import jpa.step1.repository.FavoriteRepository;

@DataJpaTest
public class FavoriteTest {

	@Autowired
	private FavoriteRepository favoriteRepository;

	@DisplayName("Favorite 생성")
	@Test
	void given_favorite_when_save_then_return_created_favorite() {
		Favorite favorite = new Favorite();

		Favorite actual = favoriteRepository.save(favorite);

		assertThat(actual.getId()).isNotNull();
	}

	@DisplayName("Favorite 조회")
	@Test
	void given_favorite_when_save_and_findById_then_return_created_favorite() {
		Favorite favorite = new Favorite();
		Favorite createdFavorite = favoriteRepository.save(favorite);

		Favorite actual = favoriteRepository.findById(1L)
			.orElseThrow(IllegalArgumentException::new);

		assertAll(
			() -> assertThat(actual.getId()).isEqualTo(createdFavorite.getId()),
			() -> assertThat(actual == favorite).isTrue()
		);
	}

}
