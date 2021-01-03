package jpa.repository;

import jpa.domain.Favorite;
import jpa.domain.Member;
import jpa.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberAndFavoriteRelationTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StationRepository stationRepository;

    @Test
    @DisplayName("사용자는 여러 즐겨찾기를 저장할 수 있다.")
    void saveWithFavorite() {
        Member expected = new Member();
        expected.addFavorite(favoriteRepository.save(new Favorite()));
        expected.addFavorite(favoriteRepository.save(new Favorite()));
        Member actual = memberRepository.save(expected);
        assertThat(actual.getFavorites().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("즐겨찾기에 동일한 사용자를 지정하여 저장한다.")
    void saveWithMember() {
        Favorite favorite1 = favoriteRepository.save(new Favorite());
        Favorite favorite2 = favoriteRepository.save(new Favorite());
        Member member = memberRepository.save(new Member());
        favorite1.setMember(member);
        favorite2.setMember(member);
        favoriteRepository.flush();
        assertThat(favorite1.getMember().getFavorites().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("즐겨찾기에 출발역과 종료역을 저장한다.")
    void saveWithStations() {
        Favorite expected = new Favorite(saveStationByName("교대역"), saveStationByName("잠실역"));
        Favorite actual = favoriteRepository.save(expected);
        assertThat(actual.getStartStation()).isEqualTo(stationRepository.findByName("교대역"));
        assertThat(actual.getEndStation()).isEqualTo(stationRepository.findByName("잠실역"));
    }

    @Test
    @DisplayName("사용자의 즐겨찾기 출발역, 종료역을 조회한다.")
    void findFavoriteStations() {
        Member expected = new Member("yhjeong1009@gmail.com");
        Station start = saveStationByName("교대역");
        Station end = saveStationByName("잠실역");
        expected.addFavorite(favoriteRepository.save(new Favorite(start, end)));
        expected.addFavorite(favoriteRepository.save(new Favorite(start, saveStationByName("강남역"))));
        Member actual = memberRepository.save(expected);

        assertThat(actual.getFavorites().size()).isEqualTo(2);
        assertThat(actual.getFavorites().stream().findAny().get().getStartStation())
                .isEqualTo(stationRepository.findByName("교대역"));
    }

    private Station saveStationByName(String name) {
        return stationRepository.save(new Station(name));
    }

}