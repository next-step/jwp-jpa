package jpa.repository;

import jpa.entity.Favorite;
import jpa.entity.Member;
import jpa.entity.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FavoriteRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    Station 강남역;
    Station 당산역;
    Station 잠실역;

    @BeforeEach
    void setup() {
        강남역 = stationRepository.save(Station.builder().name("강남역").build());
        당산역 = stationRepository.save(Station.builder().name("당산역").build());
        잠실역 = stationRepository.save(Station.builder().name("잠실역").build());

        favoriteRepository.save(Favorite.builder()
                .sourceStation(강남역)
                .destinationStation(당산역)
                .build());

        favoriteRepository.save(Favorite.builder()
                .sourceStation(강남역)
                .destinationStation(잠실역)
                .build());
    }

    @Test
    void create() {
        Member member = memberRepository.save(Member.builder().age(20).email("chh").password("1234").build());
        memberRepository.save(member);

        Station 등촌역 = Station.builder().name("등촌역").build();
        Station 염창역 = stationRepository.save(Station.builder().name("염창역").build());

        Favorite favorite = Favorite.builder().sourceStation(등촌역).destinationStation(염창역).build();
        assertThat(favorite).isEqualTo(Favorite.builder().sourceStation(등촌역).destinationStation(염창역).build());
    }

    @Test
    void addMember() {
        // given
        Favorite expected = Favorite.builder()
                                .sourceStation(강남역)
                                .destinationStation(잠실역)
                                .build();

        // when
        expected.addMember(Member.builder().age(20).email("chh").password("1234").build());
        expected.addMember(Member.builder().age(23).email("chh").password("1234").build());

        // then
        assertThat(expected.getMembers().size()).isEqualTo(2);
    }

    @Test
    void deleteMember() {
        // given
        Station 등촌역 = stationRepository.save(Station.builder().name("등촌역").build());
        Station 염창역 = stationRepository.save(Station.builder().name("염창역").build());
        Favorite favorite = Favorite.builder().sourceStation(등촌역).destinationStation(염창역).build();

        Member member = memberRepository.save(Member.builder().age(23).email("chh").password("1234").build());
        favorite.addMember(member);

        // when
        favorite.deleteMember(member);

        // then
        assertThat(favorite.getMembers().size()).isEqualTo(0);
    }

    @Test
    void update() {
        // given
        Station 등촌역 = stationRepository.save(Station.builder().name("등촌역").build());
        Station 당산역 = stationRepository.findByName("당산역");

        Favorite expected = Favorite.builder()
                .sourceStation(등촌역)
                .destinationStation(당산역)
                .build();

        // when
        Favorite actual = favoriteRepository.save(expected);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
