package jpa.repository;

import jpa.domain.favorite.Favorite;
import jpa.domain.favorite.FavoriteRepository;
import jpa.domain.station.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FavoriteRepositoryTest {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Test
    void save() {
        Favorite expected = new Favorite(new Station("선릉역"), new Station("의정부역"));
        Favorite actual = favoriteRepository.save(expected);
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void findByAll() {
        favoriteRepository.save(new Favorite(new Station("선릉역"), new Station("의정부역")));
        List<Favorite> favoriteList = favoriteRepository.findAll();
        assertThat(favoriteList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("즐겨찾기에는 출발역과 도착역이 포함되는지 확인하는 테스트")
    void findDepartureArrivalStation() {
        //given
        Station departureStation = new Station("선릉역");
        Station arrivalStation = new Station("의정부역");

        //when
        Favorite favorite = new Favorite(departureStation, arrivalStation);
        Favorite savedFavorite = favoriteRepository.save(favorite);

        //then
        assertThat(savedFavorite.getDepartureStation().getName()).isEqualTo(departureStation.getName());
        assertThat(savedFavorite.getArrivalStation().getName()).isEqualTo(arrivalStation.getName());
    }
}
