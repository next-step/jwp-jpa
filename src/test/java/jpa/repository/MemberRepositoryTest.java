package jpa.repository;

import jpa.favorite.Favorite;
import jpa.favorite.FavoriteRepository;
import jpa.member.Member;
import jpa.member.MemberRepository;
import jpa.station.Station;
import jpa.station.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository members;

    @Autowired
    private StationRepository stations;

    @Autowired
    private FavoriteRepository favorites;

    @Test
    void save() {
        int age = 32;
        String email = "suahnn@gmail.com";
        String password = "1234";
        Member expected = new Member(age, email, password);
        Member actual = members.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getAge()).isEqualTo(expected.getAge()),
                () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
                () -> assertThat(actual.getPassword()).isEqualTo(expected.getPassword())
        );
    }

    @Test
    void addFavorite() {
        int age = 32;
        String email = "suahnn@gmail.com";
        String password = "1234";
        Station fromStation = stations.save(new Station("이대역"));
        Station toStation = stations.save(new Station("신촌역"));
        Member actual = new Member(age, email, password);

        Favorite favorite = new Favorite(actual, fromStation, toStation);
        actual.addFavorite(favorite);
        members.save(actual);

        Favorite expected = favorites.findAll().get(0);

        assertThat(actual.getFavorites().get(0).getFromStation().getId())
                .isEqualTo(expected.getFromStation().getId());
        assertThat(actual.getFavorites().get(0).getToStation().getId())
                .isEqualTo(expected.getToStation().getId());
    }

    @Test
    void addFavoriteFromToSameStation() {
        Member member = members.save(new Member(32, "suahnn@gmail.com", "1234"));
        Station station = stations.save(new Station("이대역"));

        assertThatIllegalArgumentException().isThrownBy(() -> new Favorite(member, station, station));
    }
}
