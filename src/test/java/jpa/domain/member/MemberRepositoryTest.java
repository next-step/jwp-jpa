package jpa.domain.member;

import jpa.domain.favorite.Favorite;
import jpa.domain.favorite.FavoriteRepository;
import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
    }

    @Test
    void update() {
        // given
        Member member = members.save(new Member("jason"));

        // when
        member.changeName("mkkim");
        member.changeAge(32);
        Favorite favorite = saveFavorite();
        members.flush();

    }

    private Favorite saveFavorite() {
        Station departureStation = stations.save(new Station("강남역"));
        Station arrivalStation = stations.save(new Station("잠실역"));
        Member member = members.save(new Member("김민균"));
        Favorite favorite = favorites.save(new Favorite(departureStation, arrivalStation, member));
        return favorite;
    }

    @Test
    void delete() {
        Member member = members.save(new Member("mkkim"));
        members.flush();

        members.delete(member);
        members.flush();
    }
}
