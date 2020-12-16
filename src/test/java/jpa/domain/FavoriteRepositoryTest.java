package jpa.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import jpa.domain.entity.Favorite;
import jpa.domain.repository.FavoriteRepository;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FavoriteRepositoryTest {

	@Autowired
	private FavoriteRepository favoriteRepository;

	@BeforeEach
	void setup() {
		favoriteRepository.save(new Favorite());
		favoriteRepository.save(new Favorite());
	}

	@DisplayName("단일 조회 테스트")
	@Test
	void findById() {
		Favorite actual = favoriteRepository.findById(1L).get();
		assertAll(
			() -> assertThat(actual).isNotNull(),
			() -> assertThat(actual.getId()).isNotNull()
		);
	}

	@DisplayName("전체 조회 테스트")
	@Test
	void findAll() {
		int expectedLength = 2;

		List<Favorite> actualAll = favoriteRepository.findAll();

		assertThat(actualAll).hasSize(expectedLength);
	}

	@Test
	@DisplayName("insert 테스트")
	void insert() {
		int expectedLength = 3;

		Favorite newFavorite = new Favorite();
		favoriteRepository.save(newFavorite);
		List<Favorite> actualAll = favoriteRepository.findAll();

		assertThat(actualAll).hasSize(expectedLength);
	}

	@Test
	@DisplayName("delete 테스트")
	void delete() {
		int expectedLength = 1;

		Favorite station = favoriteRepository.getOne(1L);
		favoriteRepository.delete(station);
		List<Favorite> actualAll = favoriteRepository.findAll();

		assertThat(actualAll).hasSize(expectedLength);
	}
}