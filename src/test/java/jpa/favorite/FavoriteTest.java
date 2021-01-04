package jpa.favorite;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.station.Station;
import jpa.station.StationRepository;

@DataJpaTest
class FavoriteTest {

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private FavoriteRepository favoriteRepository;

	@Test
	void initObjectTest() {
		// given
		Station start = 전철_역_생성("주안");
		Station end = 전철_역_생성("시청");

		// when
		Favorite favorite = new Favorite(start, end);
		Favorite actual = favoriteRepository.save(favorite);

		// then
		assertAll(
			() -> assertThat(actual).isNotNull(),
			() -> assertThat(actual.getStartStation().getName()).isEqualTo("주안"),
			() -> assertThat(actual.getEndStation().getName()).isEqualTo("시청")
		);

	}

	private Station 전철_역_생성(String name) {
		return stationRepository.save(new Station(name));
	}
}
