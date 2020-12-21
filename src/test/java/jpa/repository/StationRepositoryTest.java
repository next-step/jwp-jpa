package jpa.repository;

import jpa.FromTo;
import jpa.entity.Favorite;
import jpa.entity.Member;
import jpa.entity.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class StationRepositoryTest {
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void save() {
        Station expected = new Station("잠실역");
        Station actual = stationRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByName() {
        String expected = "잠실역";
        stationRepository.save(new Station(expected));
        String actual = stationRepository.findByName(expected).getName();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void identity() {
        Station station1 = stationRepository.save(new Station("잠실역"));
        Station station2 = stationRepository.findById(station1.getId()).get();
        assertThat(station1 == station2).isTrue();

        Station station3 = stationRepository.findByName("잠실역");
        assertThat(station1 == station3).isTrue();
    }

    @Test
    @DisplayName("즐겨찾기 save 테스트")
    void saveFavorite() {
        Station startStation = stationRepository.save(new Station("잠실역"));
        Station endStation = stationRepository.save(new Station("홍대입구역"));
        Favorite favorite = favoriteRepository.save(new Favorite(new Member("29")));
        startStation.setFavorite(favorite, FromTo.START);
        endStation.setFavorite(favorite, FromTo.END);
        Favorite result = favoriteRepository.findById(1L).get();
        assertThat(result.getFromToStations().get(FromTo.START).getName()).isEqualTo("잠실역");
        assertThat(result.getFromToStations().get(FromTo.END).getName()).isEqualTo("홍대입구역");
    }

    @Test
    @DisplayName("즐겨찾기 중복 save 테스트")
    void duplicateSaveFavorite() {
        Station startStation = stationRepository.save(new Station("잠실역"));
        stationRepository.flush();
        Member member = memberRepository.save(new Member(27));
        Favorite favorite = favoriteRepository.save(new Favorite(member));
        Favorite favorite2 = favoriteRepository.save(new Favorite(member));
        favoriteRepository.flush();
        startStation.setFavorite(favorite, FromTo.START);
        startStation.setFavorite(favorite2, FromTo.START);
        Station result = stationRepository.findByName("잠실역");
        assertThat(result.getFavorite().getId()).isEqualTo(2L);
        //assertThat(result.getFromToStations().get(FromTo.START).getName()).isEqualTo("홍대입구역");
    }

}
