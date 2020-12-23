package jpa.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.domain.Favorite;
import jpa.domain.Station;

/**
 * @author : byungkyu
 * @date : 2020/12/20
 * @description :
 **/
@DataJpaTest
public class FavoriteRepositoryTest {
	@Autowired
	private FavoriteRepository favorites;
	@Autowired
	private StationRepository stations;

	@DisplayName("즐겨찾기에는 출발역과 도착 역이 포함되어 있다.")
	@Test
	void save() {

		Station startingStation = stations.save(new Station("강남역"));
		Station destinationStation = stations.save(new Station("잠실역"));

		Favorite expected = favorites.save(new Favorite(startingStation, destinationStation));

		assertAll(
			() -> assertThat(expected.getId()).isNotNull(),
			() -> assertThat(expected.getCreateDate()).isNotNull(),
			() -> assertThat(expected.getModifiedDate()).isNotNull(),
			() -> assertThat(expected.getStartingStation()).isEqualTo(startingStation),
			() -> assertThat(expected.getStation()).isEqualTo(destinationStation)
		);
	}
}
