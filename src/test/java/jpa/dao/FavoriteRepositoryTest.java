package jpa.dao;

import jpa.domain.Favorite;
import jpa.domain.Member;
import jpa.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FavoriteRepositoryTest {
    @Autowired
    private FavoriteRepository favorites;

    @Autowired
    private MemberRepository member;

    @Autowired
    private StationRepository station;

    @Test
    void save() {
        final Favorite expected = new Favorite();
        final Favorite actual = favorites.save(expected);
        assertThat(expected == actual).isTrue();
    }

    @Test
    void findById() {
        final Favorite expected = new Favorite();
        favorites.save(expected);
        final Optional<Favorite> actual = favorites.findById(expected.getId());
        assertThat(actual.get().getId()).isEqualTo(expected.getId());
    }

    @Test
    void update() {
        final Favorite expected = new Favorite();
        ZoneId zone = ZoneId.of("Asia/Seoul");
        expected.chageDate(zone);
        final Favorite actual = favorites.save(expected);
        assertThat(actual.getModified_date()).isEqualTo(actual.getCreated_date());
    }

    @Test
    void delete() {
        final Favorite expected = new Favorite();
        favorites.save(expected);
        favorites.deleteById(expected.getId());
        final List<Favorite> actual = favorites.findAll();
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("즐겨찾기가 어떤 멤버에 있는지 확인(저장과 조회)")
    void searchMember() {
        Member member1 = member.save(new Member());
        Favorite favorite = new Favorite();
        favorite.setMember(member1);
        favorite.setStarting(station.save(new Station("공덕역")));
        favorite.setArrival(station.save(new Station("여의도역")));
        Favorite actual = favorites.save(favorite);
        assertThat(actual.getMemeber()).isNotNull();
    }

    @Test
    @DisplayName("수정")
    void updateWithMember() {
        Member member1 = member.save(new Member());
        Favorite favorite = new Favorite();
        favorite.setMember(member1);
        favorite.setStarting(station.save(new Station("공덕역")));
        favorite.setArrival(station.save(new Station("여의도역")));
        Favorite actual = favorites.save(favorite);
        Member member2 = member.save(new Member());
        member2.setAge(30);
        actual.setMember(member2);
        favorites.flush();
        assertThat(actual.getMemeber().getAge()).isEqualTo(30);
    }

    @Test
    @DisplayName("제거")
    void removeWithMember() {
        Member member1 = member.save(new Member());
        Favorite favorite = new Favorite();
        favorite.setMember(member1);
        favorite.setStarting(station.save(new Station("공덕역")));
        favorite.setArrival(station.save(new Station("여의도역")));
        Favorite actual = favorites.save(favorite);
        actual.setMember(null);
        favorites.flush();
        assertThat(actual.getMemeber()).isNull();
    }
}
