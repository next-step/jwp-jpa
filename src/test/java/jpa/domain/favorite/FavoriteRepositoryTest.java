package jpa.domain.favorite;

import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FavoriteRepositoryTest{
    @Autowired
    FavoriteRepository favorites;

    @Autowired
    StationRepository stations;

    @Test
    void save() {
        favorites.save(new Favorite());
    }

    @DisplayName("출발역과 도착역 세팅")
    @Test
    void setDepartureStationAndsetArrivalStation() {
        // given
        Station departureStation = stations.save(new Station("천호역"));
        Station arrivalStation = stations.save(new Station("여의도역"));

        // when
        Favorite favorite = favorites.save(new Favorite(departureStation, arrivalStation));

        // then
        assertThat(favorite.getDepartureStation()).isNotNull();
        assertThat(favorite.getArrivalStation()).isNotNull();
        assertThat(favorite.getMember()).isNull();
    }
}
