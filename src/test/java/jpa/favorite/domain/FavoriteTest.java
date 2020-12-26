package jpa.favorite.domain;

import jpa.favorite.domain.Favorite;
import jpa.favorite.domain.FavoriteRepository;
import jpa.station.domain.Station;
import jpa.station.domain.StationRepository;
import jpa.member.domain.Member;
import jpa.member.domain.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FavoriteTest {
    @Autowired
    private MemberRepository members;
    @Autowired
    private StationRepository stations;
    @Autowired
    private FavoriteRepository favorites;

    private Favorite favorite;

    @BeforeEach
    void beforeEach() {
        Member member = members.save(new Member(20, "test@gmail.com", "Abcde1234!@#"));
        Station gangnamStation = stations.save(new Station("강남"));
        Station jamsilStation = stations.save(new Station("잠실"));
        favorite = new Favorite(gangnamStation, jamsilStation, member);
    }

    @DisplayName("`Favorite` 객체 생성")
    @Test
    void save() {
        // When
        Favorite actual = favorites.save(favorite);
        // Then
        assertAll(
                () -> assertNotNull(favorite),
                () -> assertSame(actual, favorite)
        );
    }

    @DisplayName("트랜잭션 안에서 `Favorite` 객체 삭제")
    @Test
    void delete() {
        // When
        favorites.delete(favorite);
        // Then
        assertNotNull(favorite);
    }
}
