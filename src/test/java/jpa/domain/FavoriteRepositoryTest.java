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
import jpa.domain.entity.Station;
import jpa.domain.repository.FavoriteRepository;
import jpa.domain.repository.StationRepository;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FavoriteRepositoryTest {

	@Autowired
	private FavoriteRepository favoriteRepository;

	@Autowired
	private StationRepository stationRepository;

	@BeforeEach
	void setup() {
		Station station1 = stationRepository.save(Station.create("사당역"));
		Station station2 = stationRepository.save(Station.create("서울숲역"));
		Station station3 = stationRepository.save(Station.create("광화문역"));

		favoriteRepository.save(Favorite.create(station1, station2));
		favoriteRepository.save(Favorite.create(station2, station3));
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

		Station station1 = stationRepository.save(Station.create("김포공항역"));
		Station station2 = stationRepository.save(Station.create("인천공항역"));
		Favorite newFavorite = Favorite.create(station1, station2);
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

	@Test
	@DisplayName("즐겨찾기에는 출발역과 도착역이 포함되어 있다.")
	void favorite() {

		Favorite favorite = favoriteRepository.findById(1L).get();
		Station departure = favorite.getDepartureStation();
		Station arrival = favorite.getArrivalStation();

		assertAll(
			() -> assertThat(departure).isNotNull(),
			() -> assertThat(arrival).isNotNull()
		);

	}
}