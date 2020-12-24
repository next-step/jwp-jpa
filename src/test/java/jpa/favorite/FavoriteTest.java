package jpa.favorite;

import static org.assertj.core.api.Assertions.*;

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

		Station start = stationRepository.save(new Station("주안역"));
		Station end = stationRepository.save(new Station("시청역"));

		Favorite favorite = new Favorite(start, end);
		Favorite actual = favoriteRepository.save(favorite);

		assertThat(actual.startName()).isEqualTo("주안역");
		assertThat(actual.endName()).isEqualTo("시청역");
	}
}
