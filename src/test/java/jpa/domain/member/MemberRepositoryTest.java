package jpa.domain.member;

import jpa.domain.favorite.FavoriteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    MemberRepository members;

    @Test
    void save() {
        Member expected = new Member("jason");
        Member actual = members.save(expected);
        assertAll(
                () -> assertThat(actual.getCreatedDate()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }
}
