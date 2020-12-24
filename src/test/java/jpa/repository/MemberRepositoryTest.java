package jpa.repository;

import jpa.domain.Favorite;
import jpa.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository members;

    @Test
    void save() {
        Member expected = new Member(28, "devyyskr@gmail.com", "12345");
        Member actual = members.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getAge()).isEqualTo(expected.getAge()),
                () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
                () -> assertThat(actual.getPassword()).isEqualTo(expected.getPassword())
        );
    }

    @Test
    void findById() {
        members.save(new Member());
        assertThat(members.findById(1L)).isNotNull();
    }
}
