package jpa;

import jpa.domain.Favorite;
import jpa.domain.FavoriteRepository;
import jpa.domain.Station;
import jpa.domain.StationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class FavoriteRepositoryTest {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private StationRepository stationRepository;

    @Test
    void save() {
        Favorite expected = new Favorite();
        Favorite actual = favoriteRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull()
        );
    }

    @Test
    void findById() {
        Favorite expected = new Favorite();
        Favorite saved = favoriteRepository.save(expected);
        Optional<Favorite> byId = favoriteRepository.findById(saved.getId());
        assertThat(byId.get().getId()).isEqualTo(expected.getId());
    }

    @Test
    void update() {
        Favorite saved = favoriteRepository.save(new Favorite());

        LocalDateTime expected = LocalDateTime.of(2020, 12, 23, 0, 0, 0);
        saved.changeUpdateTime(expected);

        Optional<Favorite> actual = favoriteRepository.findById(saved.getId());
        assertThat(actual.get().getUpdateDate()).isEqualTo(expected);
    }

    @Test
    void deleteById() {
        Favorite expected = favoriteRepository.save(new Favorite());

        favoriteRepository.deleteById(expected.getId());
        favoriteRepository.flush();

        Optional<Favorite> actual = favoriteRepository.findById(expected.getId());
        assertThat(actual).isNotPresent();
    }

    @Test
    @DisplayName("출발역, 도착역 조회")
    void startStation_test() {
        Station start = new Station("대화");
        Station end = new Station("오금");
        Station savedStartStation = stationRepository.save(start);
        Station savedEndStation = stationRepository.save(end);

        Favorite favorite = new Favorite(start, end);
        Favorite save = favoriteRepository.save(favorite);

        Optional<Favorite> found = favoriteRepository.findById(save.getId());
        assertThat(found.get().getStartStation()).isEqualTo(savedStartStation);
        assertThat(found.get().getEndStations()).isEqualTo(savedEndStation);
    }
}