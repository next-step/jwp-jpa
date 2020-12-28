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
public class FavoriteRepositoryTest {

    @Autowired
    private MemberRepository members;

    @Autowired
    private FavoriteRepository favorites;

    @Autowired
    private StationRepository stations;

    @Test
    void save() {
        Member member = members.save(new Member(32, "suahnn@gmail.com", "1234"));
        Station fromStation = stations.save(new Station("이대역"));
        Station toStation = stations.save(new Station("신촌역"));
        Favorite expected = new Favorite(member, fromStation, toStation);

        Favorite actual = favorites.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getMember().getId()).isEqualTo(member.getId()),
                () -> assertThat(actual.getFromStation().getId()).isEqualTo(fromStation.getId()),
                () -> assertThat(actual.getToStation().getId()).isEqualTo(toStation.getId())
        );
    }

    @Test
    void saveFromToSameStationThrowsException() {
        Member member = members.save(new Member(32, "suahnn@gmail.com", "1234"));
        Station station = stations.save(new Station("이대역"));

        assertThatIllegalArgumentException().isThrownBy(() -> new Favorite(member, station, station));
    }
}
