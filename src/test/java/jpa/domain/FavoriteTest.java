package jpa.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class FavoriteTest {

    @Autowired
    private FavoriteRepository favorites;
    @Autowired
    private StationRepository stations;

    @Test
    void save() {
        Station jongno3ga = new Station("종로3가");
        Station jonggak = new Station("종각");
        Favorite expected = new Favorite(jonggak, jongno3ga);
        Favorite actual = favorites.save(expected);
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void read() {
        Station jongno3ga = new Station("종로3가");
        Station jonggak = new Station("종각");
        Favorite actual = favorites.save(new Favorite(jonggak, jongno3ga));
        Long id = actual.getId();
        Favorite favorite = favorites.findById(id).get();
        assertThat(actual).isEqualTo(favorite);
    }

    @Test
    void update() {
        //given
        Station jongno3ga = stations.save( new Station("종로3가"));
        Station jonggak = stations.save(new Station("종각"));
        Station jongno5ga = stations.save(new Station("종로5가"));
        Favorite actual = favorites.save(new Favorite(jonggak, jongno3ga));
        LocalDateTime modifiedDate = actual.getModifiedDate();
        favorites.flush();

        //when
        actual.updateDestination(jongno5ga);
        Favorite favorite = favorites.save(actual);
        favorites.flush();

        //then
        assertThat(favorite.getModifiedDate()).isAfter(modifiedDate);
    }

    @Test
    void delete() {
        Station jongno3ga = new Station("종로3가");
        Station jonggak = new Station("종각");
        Favorite actual = favorites.save(new Favorite(jonggak, jongno3ga));
        Long id = actual.getId();
        favorites.delete(actual);
        Optional<Favorite> byId = favorites.findById(id);
        assertThat(byId).isNotPresent();
    }


}
