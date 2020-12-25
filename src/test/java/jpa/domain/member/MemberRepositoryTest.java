package jpa.domain.member;

import jpa.domain.favorite.Favorite;
import jpa.domain.favorite.FavoriteRepository;
import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    MemberRepository members;

    @Autowired
    StationRepository stations;

    @Autowired
    FavoriteRepository favorites;

    @Test
    void save() {
        Member expected = new Member("jason");
        Member actual = members.save(expected);
        assertAll(
                () -> assertThat(actual.getCreatedDate()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void update() {
        // given
        Member member = members.save(new Member("jason"));

        // when
        member.changeName("mkkim");
        member.changeAge(32);
        Favorite favorite = saveFavorite();
        member.addFavorites(favorite);
        members.flush();

        // then
        assertThat(members.findByName("mkkim").getAge()).isEqualTo(32);
    }

    private Favorite saveFavorite() {
        Station departureStation = stations.save(new Station("강남역"));
        Station arrivalStation = stations.save(new Station("잠실역"));
        Favorite favorite = favorites.save(new Favorite(departureStation, arrivalStation));
        return favorite;
    }

    @Test
    void delete() {
        Member member = members.save(new Member("mkkim"));
        member.addFavorites(saveFavorite());
        members.flush();

        members.delete(member);
        members.flush();
    }
}
