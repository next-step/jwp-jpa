package jpa;

import jpa.domain.Favorite;
import jpa.domain.Station;
import jpa.repository.FavoriteRepository;
import jpa.repository.LineRepository;
import jpa.repository.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FavoriteRepositoryTest {

	@Autowired
	private FavoriteRepository favoriteRepository;

	@Autowired
	private StationRepository stationRepository;

	@Test
	void 즐겨찾기에는_출발역과_도착역이_포함되어_있다(){
		Station departure = new Station("왕십리역");
		Station arrival = new Station("강남역");
		stationRepository.save(departure);
		stationRepository.save(arrival);


		Favorite favorite = new Favorite();
		favorite.setDepartureStation(departure);
		favorite.setArrivalStation(arrival);
		favoriteRepository.save(favorite);

		Favorite findDeparture = favoriteRepository.findByDepartureStation(departure);
		assertThat(findDeparture.getDepartureStation()).isEqualTo(departure);
		Favorite findArrival = favoriteRepository.findByArrivalStation(arrival);
		assertThat(findArrival.getArrivalStation()).isEqualTo(arrival);
	}
}
