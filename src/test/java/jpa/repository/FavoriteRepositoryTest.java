package jpa.repository;

import jpa.domain.Favorite;
import jpa.domain.Member;
import jpa.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FavoriteRepositoryTest {
    @Autowired
    private FavoriteRepository favoritesRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StationRepository stationRepository;

    @Test
    void save() {
        final Favorite expected = new Favorite();
        final Favorite actual = favoritesRepository.save(expected);
        assertThat(expected == actual).isTrue();
    }

    @Test
    void findById() {
        final Favorite expected = new Favorite();
        favoritesRepository.save(expected);
        final Optional<Favorite> actual = favoritesRepository.findById(expected.getId());
        assertThat(actual.get().getId()).isEqualTo(expected.getId());
    }

    @Test
    void update() {
        final Favorite expected = new Favorite();
        ZoneId zone = ZoneId.of("Asia/Seoul");
        expected.chageDate(zone);
        final Favorite actual = favoritesRepository.save(expected);
        assertThat(actual.getModified_date()).isEqualTo(actual.getCreated_date());
    }

    @Test
    void delete() {
        final Favorite expected = new Favorite();
        favoritesRepository.save(expected);
        favoritesRepository.deleteById(expected.getId());
        final List<Favorite> actual = favoritesRepository.findAll();
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("즐겨찾기가 어떤 멤버에 있는지 확인(저장과 조회)")
    void searchMember() {
        Member member1 = memberRepository.save(new Member());
        Favorite favorite = new Favorite();
        favorite.setMember(member1);
        favorite.setStarting(stationRepository.save(new Station("공덕역")));
        favorite.setArrival(stationRepository.save(new Station("여의도역")));
        Favorite actual = favoritesRepository.save(favorite);
        assertThat(actual.getMemeber()).isNotNull();
    }

    @Test
    @DisplayName("수정")
    void updateWithMember() {
        Member member1 = memberRepository.save(new Member());
        Favorite favorite = new Favorite();
        favorite.setMember(member1);
        favorite.setStarting(stationRepository.save(new Station("공덕역")));
        favorite.setArrival(stationRepository.save(new Station("여의도역")));
        Favorite actual = favoritesRepository.save(favorite);
        Member member2 = memberRepository.save(new Member());
        member2.setAge(30);
        actual.setMember(member2);
        favoritesRepository.flush();
        assertThat(actual.getMemeber().getAge()).isEqualTo(30);
    }

    @Test
    @DisplayName("제거")
    void removeWithMember() {
        Member member1 = memberRepository.save(new Member());
        Favorite favorite = new Favorite();
        favorite.setMember(member1);
        favorite.setStarting(stationRepository.save(new Station("공덕역")));
        favorite.setArrival(stationRepository.save(new Station("여의도역")));
        Favorite actual = favoritesRepository.save(favorite);
        actual.setMember(null);
        favoritesRepository.flush();
        assertThat(actual.getMemeber()).isNull();
    }
}
