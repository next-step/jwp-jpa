package jpa.member.domain;

import jpa.favorite.domain.Favorite;
import jpa.member.domain.Member;
import jpa.member.domain.MemberRepository;
import jpa.station.domain.Station;
import jpa.station.domain.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberTest {
    @Autowired
    private MemberRepository members;
    @Autowired
    private StationRepository stations;
    @Autowired
    private TestEntityManager em;
    private Member member;

    @BeforeEach
    void beforeEach() {
        member = new Member(20, "giveawesome@gmail.com", "AbCdEf123!@");
    }

    @DisplayName("`Member` 객체 저장")
    @Test
    void save() {
        // When
        Member actual = members.save(member);
        // Then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertSame(actual, member)
        );
    }

    @DisplayName("이미 저장된 `Member`와 찾게된 `Member`의 동일성 여부 확인")
    @Test
    void findByEmail() {
        // Given
        members.save(member);
        // When
        Member actual = members.findByEmail("giveawesome@gmail.com")
                .stream()
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지않는 이메일입니다."));
        // Then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertSame(actual, member)
        );
    }

    @DisplayName("`Member` 객체의 이메일 변경")
    @Test
    void changeEmail() {
        // Given
        String expected = "changed@gmail.com";
        Member actual = members.save(member);
        // When
        actual.changeEmail(expected);
        // Then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertThat(actual.getEmail()).isEqualTo(expected)
        );
    }

    @DisplayName("트랜잭션 안에서 `Member` 객체 삭제")
    @Test
    void deleteInTransaction() {
        // When
        members.delete(member);
        // Then
        assertAll(
                () -> assertNotNull(member)
        );
    }

    @DisplayName("엔티티 매니저로 1차 캐시를 관리하여, `Member` 객체 삭제")
    @Test
    void deleteWithEntityManager() {
        // Given
        long id = em.persistAndGetId(member, Long.class);
        // When
        members.delete(member);
        em.flush();
        Member actual = em.find(Member.class, id);
        // Then
        assertNull(actual);
    }

    @Test
    @DisplayName("`Member`는 다수의 `Favorite` 추가")
    void addFavorite() {
        // Given
        members.save(member);
        Station seokChonStation = stations.save(new Station("석촌"));
        Station gangNamStation = stations.save(new Station("강남"));
        // When
        member.addFavorite(seokChonStation, gangNamStation);
        member.addFavorite(gangNamStation, seokChonStation);
        List<Favorite> actual = member.getFavorites();
        // Then
        assertThat(actual).hasSize(2);
    }

    @DisplayName("`Member`는 가진 Favorite 삭제")
    @Test
    void removeFavorite() {
        // Given
        members.save(member);
        Station seokChonStation = stations.save(new Station("석촌"));
        Station gangNamStation = stations.save(new Station("강남"));
        member.addFavorite(seokChonStation, gangNamStation);
        // When
        Favorite favorite = member.getFavorites().stream().findAny().orElseThrow(IllegalArgumentException::new);
        member.removeFavorite(favorite);
        // Then
        assertThat(member.getFavorites()).isEmpty();
    }
}
