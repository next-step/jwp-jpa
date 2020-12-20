package jpa.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class FavoriteRepositoryTest {

	@Autowired
	private FavoriteRepository favoriteRepository;
	@Autowired
	private StationRepository stationRepository;

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

	@Test
	@DisplayName("즐겨찾기에는 출발역과 도착역이 포함되어 있다.")
	void containsStartAndDestination() {
		Station startStation = stationRepository.save(new Station("문래역"));
		Station destinationStation = stationRepository.save(new Station("잠실역"));
		Favorite expected = favoriteRepository.save(new Favorite(startStation, destinationStation));

		assertAll(
			() -> assertThat(expected.getStartStation()).isEqualTo(startStation),
			() -> assertThat(expected.getDestinationStation()).isEqualTo(destinationStation)
		);
	}
}
