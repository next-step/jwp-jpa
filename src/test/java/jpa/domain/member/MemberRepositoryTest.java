package jpa.domain.member;

import jpa.domain.favorite.Favorite;
import jpa.domain.favorite.FavoriteRepository;
import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    MemberRepository members;

    @Autowired
    StationRepository stations;

    @Autowired
    FavoriteRepository favorites;

    @Test
    void save() {
        Member expected = new Member("jason");
        Member actual = members.save(expected);
    }

    @Test
    void update() {
        // given
        Member member = members.save(new Member("jason"));

        // when
        member.change("mkkim");
        member.change(32);
        members.flush();

    }

    @DisplayName("나이 유효성 검증")
    @Test
    void validateAge() {
        Member member = members.save(new Member("mkkim"));
        assertThatThrownBy(() -> {
            member.change(-1);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void delete() {
        Member member = members.save(new Member("mkkim"));
        members.flush();

        members.delete(member);
        members.flush();
    }

}
