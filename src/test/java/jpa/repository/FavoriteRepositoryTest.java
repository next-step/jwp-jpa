package jpa.repository;

import jpa.domain.favorite.Favorite;
import jpa.domain.favorite.FavoriteRepository;
import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FavoriteRepositoryTest {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private StationRepository stationRepository;
    private Station departureStation;
    private Station arrivalStation;

    @BeforeEach
    void setUp() {
        departureStation = new Station("선릉역");
        arrivalStation = new Station("의정부역");
        stationRepository.saveAll(Arrays.asList(departureStation, arrivalStation));
    }

    @Test
    void save() {
        Favorite expected = new Favorite(departureStation, arrivalStation);
        Favorite actual = favoriteRepository.save(expected);
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void findByAll() {
        favoriteRepository.save(new Favorite(departureStation, arrivalStation));
        List<Favorite> favoriteList = favoriteRepository.findAll();
        assertThat(favoriteList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("즐겨찾기에는 출발역과 도착역이 포함되는지 확인하는 테스트")
    void findDepartureArrivalStation() {
        Favorite favorite = new Favorite(departureStation, arrivalStation);
        Favorite savedFavorite = favoriteRepository.save(favorite);

        assertThat(savedFavorite.getDepartureStation().getName()).isEqualTo(departureStation.getName());
        assertThat(savedFavorite.getArrivalStation().getName()).isEqualTo(arrivalStation.getName());
    }
}
