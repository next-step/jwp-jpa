package jpa.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class FavoriteRepositoryTest {

    @Autowired
    FavoriteRepository favorites;

    @Autowired
    StationRepository stations;

    @Test
    @DisplayName("즐겨찾기에는 출발역과 도착역이 포함되어 있다.")
    public void save() throws Exception {
        Station fromStation = stations.save(new Station("화정역"));
        Station toStation = stations.save(new Station("잠실역"));

        Favorite expected = favorites.save(new Favorite(fromStation, toStation));
        Favorite byFromStation = favorites.findByFromStation(fromStation);
        Favorite byToStation = favorites.findByToStation(toStation);

        assertAll(
                () -> assertThat(expected.getId()).isNotNull(),
                () -> assertThat(expected.getCreateDate()).isNotNull(),
                () -> assertThat(expected.getModifiedDate()).isNotNull(),
                () -> assertThat(expected.getModifiedDate()).isNotNull(),
                () -> assertThat(expected.getFromStation()).isEqualTo(fromStation),
                () -> assertThat(expected.getToStation()).isEqualTo(toStation),
                () -> assertThat(byFromStation).isEqualTo(byToStation)
		);
    }
}
