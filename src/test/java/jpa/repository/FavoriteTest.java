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
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class FavoriteTest {

    @Autowired
    private MemberRepository members;

    @Autowired
    private StationRepository stations;

    @Autowired
    private FavoriteRepository favorites;

    @Test
    void save() {
        Station fromStation = stations.save(new Station("이대역"));
        Station toStation = stations.save(new Station("신촌역"));
        Member member = members.save(new Member(32, "suahnn@gmail.com", "1234"));

        Favorite expected = new Favorite(member, fromStation, toStation);
        Favorite actual = favorites.save(expected);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getMember()).isEqualTo(expected.getMember()),
                () -> assertThat(actual.getFromStation().getId()).isEqualTo(expected.getFromStation().getId()),
                () -> assertThat(actual.getToStation().getId()).isEqualTo(expected.getToStation().getId())
        );
    }
}
