package jpa.repository;

import jpa.entity.Favorite;
import jpa.entity.Member;
import jpa.entity.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class MemberRepositoryTest {

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
    void save() {
        // given
        Member expected = Member.builder().age(25).email("nextstep.com").password("1234").build();

        // when
        Member actual = memberRepository.save(expected);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void delete() {
        // given
        Member expected = memberRepository.findByEmail("chh9975");

        // when
        memberRepository.delete(expected);

        // then
        Member deletedMember = memberRepository.findByEmail("chh9975");
        assertThat(deletedMember).isNull();
    }

    @Test
    void update() {
        // given
        Member member = memberRepository.findByEmail("chh9975");

        // when
        member.changeEmail("nextstep.com");

        Member deleteMember = memberRepository.findByEmail("chh9975");

        // then
        assertThat(deleteMember).isNull();
    }

    @Test
    void read() {
        // given
        Member expected = memberRepository.findByEmail("chh9975");

        // when
        List<Favorite> actual = expected.getFavorites();

        // then
        assertAll(
                () -> assertThat(actual.get(0).getDestinationStation().getName()).isEqualTo("당산역"),
                () -> assertThat(actual).isEqualTo(expected.getFavorites())
        );
    }
}
