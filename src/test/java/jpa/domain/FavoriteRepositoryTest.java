package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FavoriteRepositoryTest {

	@Autowired
	private FavoriteRepository favorites;

	@Test
	@DisplayName("createdDate, ModifiedDate 잘 저장되는지 확인")
	void auditing() {
		Favorite favorite = favorites.save(new Favorite());

		assertThat(favorite.getCreatedDate()).isNotNull();
		assertThat(favorite.getModifiedDate()).isNotNull();
	}
}
