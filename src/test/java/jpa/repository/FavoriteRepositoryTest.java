package jpa.repository;

import jpa.entity.Favorite;
import jpa.entity.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class FavoriteRepositoryTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private StationRepository stationRepository;

    @Test
    void create() {
        Station 등촌역 = Station.builder().name("등촌역").build();
        Station 염창역 = Station.builder().name("염창역").build();

        Favorite savedFavorite = favoriteRepository.save(Favorite.builder()
                                                            .sourceStation(등촌역)
                                                            .destinationStation(염창역)
                                                            .build()
                                                        );

        assertAll(
                () -> assertThat(savedFavorite.getId()).isNotNull(),
                () -> assertThat(savedFavorite.getSourceStation()).isEqualTo(등촌역),
                () -> assertThat(savedFavorite.getDestinationStation()).isEqualTo(염창역)
        );
    }

    @Test
    void update() {
        // given
        Station 등촌역 = stationRepository.save(Station.builder().name("등촌역").build());
        Station 당산역 = stationRepository.save(Station.builder().name("당산역").build());

        Favorite newFavorite = Favorite.builder()
                .sourceStation(Station.builder().name("등촌역").build())
                .destinationStation(Station.builder().name("당산역").build())
                .build();

        Favorite savedFavorite = favoriteRepository.save(newFavorite);

        // when
        savedFavorite.updateSourceStation(당산역);
        savedFavorite.updateDestinationStation(등촌역);

        // then
        assertThat(savedFavorite.getSourceStation()).isEqualTo(당산역);
        assertThat(savedFavorite.getDestinationStation()).isEqualTo(등촌역);
    }
}
