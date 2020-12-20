package jpa.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.domain.Favorite;

/**
 * @author : byungkyu
 * @date : 2020/12/20
 * @description :
 **/
@DataJpaTest
public class FavoriteRepositoryTest {
	@Autowired
	private FavoriteRepository favorites;

	@Test
	void save() {
		Favorite expected = favorites.save(new Favorite());

		assertAll(
			() -> assertThat(expected.getId()).isNotNull(),
			() -> assertThat(expected.getCreateDate()).isNotNull(),
			() -> assertThat(expected.getModifiedDate()).isNotNull()
		);
	}
}
