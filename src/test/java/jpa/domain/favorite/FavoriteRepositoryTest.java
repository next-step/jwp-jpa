package jpa.domain.favorite;

import jpa.domain.member.Member;
import jpa.domain.member.MemberRepository;
import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FavoriteRepositoryTest {
    private static final String ARRIVAL_STATION_NAME = "arrivalStationName";
    private static final String DEPARTURE_STATION_NAME = "departureStationName";
    private static final String MEMBER_NAME = "memberName";

    @Autowired
    FavoriteRepository favorites;

    @Autowired
    StationRepository stations;

    @Autowired
    MemberRepository members;

    @Test
    void save() {
        favorites.save(new Favorite());
    }

    @DisplayName("출발역과 도착역 세팅")
    @Test
    void setDepartureStationAndsetArrivalStation() {
        // given
        Map<String, String> param = new HashMap<String, String>();
        param.put(MEMBER_NAME, "김민균");
        param.put(ARRIVAL_STATION_NAME, "여의도역");
        param.put(DEPARTURE_STATION_NAME, "천호역");

        // when
        Favorite favorite = saveFavorite(param);

        // then
        assertThat(favorite).isNotNull();
    }

    @DisplayName("즐겨찾기 업데이트")
    @Test
    void update() {
        // given
        Map<String, String> param = new HashMap<String, String>();
        param.put(MEMBER_NAME, "김민균");
        param.put(ARRIVAL_STATION_NAME, "여의도역");
        param.put(DEPARTURE_STATION_NAME, "천호역");
        Favorite favorite = saveFavorite(param);

        // when
        Member member = members.save(new Member("포비"));
        favorite.changeMember(member);
        Favorite favorite1 = favorites.findByMember(member);
    }

    private Favorite saveFavorite(Map<String, String> param) {
        Station departureStation = stations.save(new Station(param.get(DEPARTURE_STATION_NAME)));
        Station arrivalStation = stations.save(new Station(param.get(ARRIVAL_STATION_NAME)));
        Member member = members.save(new Member(param.get(MEMBER_NAME)));

        Favorite favorite = favorites.save(new Favorite(departureStation, arrivalStation, member));
        return favorite;
    }


    @DisplayName("즐겨찾기 삭제")
    @Test
    void delete() {
        // given
        Map<String, String> param = new HashMap<String, String>();
        param.put(MEMBER_NAME, "김민균");
        param.put(ARRIVAL_STATION_NAME, "여의도역");
        param.put(DEPARTURE_STATION_NAME, "천호역");
        Favorite favorite = saveFavorite(param);

        // when
        favorites.delete(favorite);
        favorites.flush();

        // then
        assertThat(favorites.findAll()).hasSize(0);
        assertThat(members.findAll()).hasSize(1);
        assertThat(stations.findAll()).hasSize(2);
    }

    @DisplayName("즐겨찾기 가져오기")
    @Test
    void find() {
        Map<String, String> param = new HashMap<String, String>();
        param.put(MEMBER_NAME, "김민균12");
        param.put(ARRIVAL_STATION_NAME, "잠실역");
        param.put(DEPARTURE_STATION_NAME, "천호역");
        Station departureStation = stations.save(new Station(param.get(DEPARTURE_STATION_NAME)));
        Station arrivalStation = stations.save(new Station(param.get(ARRIVAL_STATION_NAME)));
        Member member = members.save(new Member(param.get(MEMBER_NAME)));

        Favorite favorite = favorites.save(new Favorite(departureStation, arrivalStation, member));
        assertThat(favorites.findByMember(member)).isEqualTo(favorite);
    }
}
