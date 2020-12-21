package jpa.repository;

import jpa.domain.favorite.Favorite;
import jpa.domain.favorite.FavoriteRepository;
import jpa.domain.member.Member;
import jpa.domain.member.MemberRepository;
import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private StationRepository stationRepository;

    @Test
    void save() {
        Member expected = new Member(30, "kim@gmail.com","qwerty1234");
        Member actual = memberRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getAge()).isEqualTo(expected.getAge()),
                () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
                () -> assertThat(actual.getPassword()).isEqualTo(expected.getPassword())
        );
    }

    @Test
    void findByName() {
        String expected = "kim@gmail.com";
        memberRepository.save(new Member(30, "kim@gmail.com","qwerty1234"));
        List<Member> memberList = memberRepository.findByEmail(expected);
        assertThat(memberList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("사용자는 여러 즐겨찾기를 가질 수 있음을 테스트")
    void findFavoritesByMember() {
        //given
        Station station1 = new Station("선릉역");
        Station station2 = new Station("의정부역");
        Station station3 = new Station("회룡역");
        Station station4 = new Station("도봉산역");
        Station station5 = new Station("삼성역");

        stationRepository.save(station1);
        stationRepository.save(station2);
        stationRepository.save(station3);
        stationRepository.save(station4);
        stationRepository.save(station5);

        Favorite favorite1 = new Favorite(station1, station2);
        Favorite favorite2 = new Favorite(station1, station3);
        Favorite favorite3 = new Favorite(station4, station5);
        favoriteRepository.save(favorite1);
        favoriteRepository.save(favorite2);
        favoriteRepository.save(favorite3);
        //when
        Member member = new Member(30, "kim@gmail.com","qwerty1234");
        member.addFavorite(favorite1);
        member.addFavorite(favorite2);
        member.addFavorite(favorite3);
        Member savedMember = memberRepository.save(member);
        //then

        Member findMember = memberRepository.findById(savedMember.getId()).get();
        assertThat(findMember.getFavorites().size()).isEqualTo(3);
        assertThat(findMember.getFavorites().get(0).getDepartureStation()).isEqualTo(station1);
        assertThat(findMember.getFavorites().get(0).getArrivalStation()).isEqualTo(station2);
        assertThat(findMember.getFavorites().get(1).getDepartureStation()).isEqualTo(station1);
        assertThat(findMember.getFavorites().get(1).getArrivalStation()).isEqualTo(station3);
        assertThat(findMember.getFavorites().get(2).getDepartureStation()).isEqualTo(station4);
        assertThat(findMember.getFavorites().get(2).getArrivalStation()).isEqualTo(station5);
    }
}
