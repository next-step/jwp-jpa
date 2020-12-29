package jpa.repository;

import jpa.entity.Favorite;
import jpa.entity.Member;
import jpa.entity.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FavoriteRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @BeforeEach
    void setup() {
        Member member1 = memberRepository.save(Member.builder().age(20).email("chh9975").password("1234").build());
        Member member2 = memberRepository.save(Member.builder().age(30).email("chh0157").password("1234").build());

        Station 강남역 = stationRepository.save(Station.builder().name("강남역").build());
        Station 당산역 = stationRepository.save(Station.builder().name("당산역").build());
        Station 잠실역 = stationRepository.save(Station.builder().name("잠실역").build());

        favoriteRepository.save(Favorite.builder()
                .sourceStation(강남역)
                .destinationStation(당산역)
                .member(member1)
                .build());

        favoriteRepository.save(Favorite.builder()
                .sourceStation(강남역)
                .destinationStation(잠실역)
                .member(member2)
                .build());
    }

    @Test
    @DisplayName("사용자 삭제 시 favorite 삭제")
    void delete() {
        // given
        Member member = memberRepository.findByEmail("chh9975");
        System.out.println(member.getEmail());

        // when
        memberRepository.delete(member);

        // then
        List<Favorite> deleteMember = favoriteRepository.findByMember(member);
        assertThat(deleteMember).isEmpty();
    }

    @Test
    void update() {
        // given
        Member member = memberRepository.findByEmail("chh9975");
        Station 등촌역 = stationRepository.save(Station.builder().name("등촌역").build());
        Station 당산역 = stationRepository.findByName("당산역");

        Favorite expected = Favorite.builder()
                .sourceStation(등촌역)
                .destinationStation(당산역)
                .member(member).build();

        // when
        Favorite actual = favoriteRepository.save(expected);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void save() {
        //given
        Member member = memberRepository.findByEmail("chh9975");

        Station 시청역 = stationRepository.save(Station.builder().name("시청역").build());
        Station 판교역 = stationRepository.save(Station.builder().name("판교역").build());

        Favorite expected = Favorite.builder()
                .sourceStation(시청역)
                .destinationStation(판교역)
                .member(member)
                .build();

        // when
        Favorite actual = favoriteRepository.save(expected);

        //then
        assertThat(actual).isEqualTo(expected);
    }
}
